package dog.snow.androidrecruittest.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.repository.daos.*
import dog.snow.androidrecruittest.repository.model.RawAlbumEntity
import dog.snow.androidrecruittest.repository.model.RawPhotoEntity
import dog.snow.androidrecruittest.repository.model.RawUserEntity
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem

@Database(entities = [ListItem::class, Detail::class, RawAlbumEntity::class, RawUserEntity::class, RawPhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun detailsDao(): DetailsDao
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
    abstract fun userDao(): UserDao
}