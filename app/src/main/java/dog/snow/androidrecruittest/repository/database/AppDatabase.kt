package dog.snow.androidrecruittest.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.repository.daos.DetailsDao
import dog.snow.androidrecruittest.repository.daos.ListDao
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem

@Database(entities = [ListItem::class, Detail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun detailsDao(): DetailsDao
}