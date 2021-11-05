package dog.snow.androidrecruittest.repository.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto

interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushAlbums(albumList: List<RawAlbum>)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getAlbum(id: Int): RawAlbum

    @Query("SELECT userId FROM Album")
    suspend fun getUsersId() : List<Int>

    @Query("SELECT * FROM Album")
    suspend fun getAlbums(): List<RawAlbum>

    @Delete
    suspend fun deleteAlbum(album: RawAlbum)
}