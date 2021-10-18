package at.technikum_wien.polzert.news

import androidx.lifecycle.LiveData
import androidx.room.*
import at.technikum_wien.polzert.news.data.NewsItem

@Dao
interface ItemDao {
    @get:Query("SELECT * FROM item ORDER BY identifier, title, link, description, imageUrl, author, publicationDate, keywords")
    val items : LiveData<List<NewsItem>>
    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: kotlin.collections.List<at.technikum_wien.polzert.news.data.NewsItem>?)
    @Update
    suspend fun update(item : NewsItem)
    @Delete
    suspend fun delete(item : NewsItem)
}