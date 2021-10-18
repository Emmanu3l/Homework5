package at.technikum_wien.polzert.news

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): Instant? {
            return value?.let { Instant.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(instant: Instant?): String? {
        return instant?.toString()
    }
}
