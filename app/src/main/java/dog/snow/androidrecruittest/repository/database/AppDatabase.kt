package dog.snow.androidrecruittest.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.repository.daos.DetailsDao
import dog.snow.androidrecruittest.repository.daos.ListDao
import dog.snow.androidrecruittest.repository.tables.DetailTable
import dog.snow.androidrecruittest.repository.tables.ListTable

@Database(entities = [ListTable::class, DetailTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun detailsDao(): DetailsDao
}