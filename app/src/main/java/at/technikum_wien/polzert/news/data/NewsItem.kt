package at.technikum_wien.polzert.news.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@kotlinx.serialization.Serializable
@Entity(tableName = "item", indices = [Index(value = ["identifier", "title", "link", "description", "imageUrl", "author", "publicationDate", "keywords"], unique = true)])
data class NewsItem(
    @PrimaryKey var identifier: String,
    var title: String,
    var link: String?,
    var description: String?,
    var imageUrl: String?,
    var author: String?,
    var publicationDate: Instant,
    var keywords: String?
)
