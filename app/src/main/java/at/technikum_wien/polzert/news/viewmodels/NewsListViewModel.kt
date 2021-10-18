package at.technikum_wien.polzert.news.viewmodels

import android.app.Application
import androidx.lifecycle.*
import at.technikum_wien.polzert.news.ApplicationDatabase
import at.technikum_wien.polzert.news.Repository
import at.technikum_wien.polzert.news.data.download.NewsDownloader
import at.technikum_wien.polzert.news.data.NewsItem
import at.technikum_wien.polzert.news.settings.UserPreferencesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val userPreferencesRepository: UserPreferencesRepository, application: Application) : ViewModel() {
    private val repository : Repository
    private val _newsItems = MutableLiveData<List<NewsItem>>(listOf())
    val identifier by lazy { MutableLiveData("") }
    val title by lazy { MutableLiveData("") }
    val link by lazy { MutableLiveData("") }
    val description by lazy { MutableLiveData("") }
    val imageUrl by lazy { MutableLiveData("") }
    val author by lazy { MutableLiveData("") }
    val publicationDate by lazy { MutableLiveData("") }
    val keywords by lazy { MutableLiveData("") }

    private val _error = MutableLiveData<Boolean>(false)
    private var lastFeedUrl : String? = null

    init {
        val itemDao = ApplicationDatabase.getDatabase(application).itemDao()
        repository = Repository(itemDao)
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesFlow.collect {
                feedUrl.value = it.feedUrl
                showImages.value = it.showImages
                downloadImages.value = it.downloadImages
                if (lastFeedUrl != it.feedUrl) {
                    downloadNewsItems(it.feedUrl)
                    lastFeedUrl = it.feedUrl
                }
            }
        }
    }

    val newsItems : LiveData<List<NewsItem>>
        get() = _newsItems
    val error : LiveData<Boolean>
        get() = _error
    val feedUrl = MutableLiveData("")
    val showImages = MutableLiveData(false)
    val downloadImages = MutableLiveData(false)

    private fun downloadNewsItems(newsFeedUrl: String) {
        _error.value = false
        _newsItems.value = listOf()
        viewModelScope.launch {
            val value = NewsDownloader().load(newsFeedUrl)
            if (value == null)
                _newsItems.value = repository.items.value
                //TODO
                //_error.value = true
            else
                _newsItems.value = value
                repository.insert(value)
        }
    }

    fun updatePreferences(feedUrl : String, showImages : Boolean, downloadImages : Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateFeedUrl(feedUrl = feedUrl)
            userPreferencesRepository.updateShowImages(showImages = showImages)
            userPreferencesRepository.updateDownloadImages(downloadImages = downloadImages)
        }
    }

    fun reload() {
        lastFeedUrl?.let { downloadNewsItems(it) }
    }
}
