package dog.snow.androidrecruittest.repository.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawPhoto

interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushPhotos(photosList: List<RawPhoto>)

    @Query("SELECT id FROM Photo")
    suspend fun getPhotosId(): List<Int>

    @Query("SELECT * FROM Details WHERE id = :id")
    suspend fun getPhoto(id: Int): RawPhoto

    @Query("SELECT albumId FROM Photo")
    suspend fun getAlbumsId() : List<Int>

    @Delete
    suspend fun deletePhotos(photo: RawPhoto)
}