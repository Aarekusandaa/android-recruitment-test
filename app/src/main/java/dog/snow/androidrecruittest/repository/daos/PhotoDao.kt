package dog.snow.androidrecruittest.repository.daos

import androidx.room.*
import dog.snow.androidrecruittest.repository.model.RawPhotoEntity

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushPhotos(photosList: List<RawPhotoEntity>) : List<Long>

    @Query("SELECT id FROM Photo")
    suspend fun getPhotosId(): List<Int>

    @Query("SELECT * FROM Photo WHERE id = :id")
    suspend fun getPhoto(id: Int): RawPhotoEntity

    @Query("SELECT albumId FROM Photo")
    suspend fun getAlbumsId() : List<Int>

    @Delete
    suspend fun deletePhotos(photo: RawPhotoEntity) : Int
}