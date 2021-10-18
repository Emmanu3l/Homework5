package at.technikum_wien.polzert.news

import at.technikum_wien.polzert.news.data.NewsItem

class Repository(private val itemDao: ItemDao) {
    val items = itemDao.items

    suspend fun insert(item: List<NewsItem>?) {
        itemDao.insert(item)
    }

    suspend fun delete(entry: NewsItem) {
        itemDao.delete(entry)
    }
}