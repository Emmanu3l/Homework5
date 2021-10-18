package at.technikum_wien.polzert.news

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.technikum_wien.polzert.news.data.NewsItem

@Database(entities = [NewsItem::class], version = 1)
@TypeConverters(Converters::class)
public abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getDatabase(context: Context): ApplicationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val tempInstance2 = INSTANCE
                if (tempInstance2 != null) {
                    return tempInstance2
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "item_list"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
