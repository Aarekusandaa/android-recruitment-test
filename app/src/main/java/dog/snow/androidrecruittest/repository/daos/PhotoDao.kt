package dog.snow.androidrecruittest.repository.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawPhoto

interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushPhotos(photosList: List<RawPhoto>)

    @Query("SELECT * FROM Photo")
    suspend fun getPhotos(): List<RawPhoto>

    @Query("SELECT * FROM Details WHERE id = :id")
    suspend fun getPhoto(id: Int): RawPhoto
}