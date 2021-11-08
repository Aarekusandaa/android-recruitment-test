package dog.snow.androidrecruittest.repository.daos

import androidx.room.*
import dog.snow.androidrecruittest.repository.model.RawAlbumEntity

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushAlbums(albumList: List<RawAlbumEntity>)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getAlbum(id: Int): RawAlbumEntity

    @Query("SELECT userId FROM Album")
    suspend fun getUsersId() : List<Int>

    @Query("SELECT * FROM Album")
    suspend fun getAlbums(): List<RawAlbumEntity>

    @Delete
    suspend fun deleteAlbum(album: RawAlbumEntity)
}