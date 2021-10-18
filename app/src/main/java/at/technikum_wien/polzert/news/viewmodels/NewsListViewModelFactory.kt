package at.technikum_wien.polzert.news.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.technikum_wien.polzert.news.settings.UserPreferencesRepository

class NewsListViewModelFactory(private val userPreferencesRepository: UserPreferencesRepository, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsListViewModel(userPreferencesRepository = userPreferencesRepository, application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
