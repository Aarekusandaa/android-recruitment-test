package dog.snow.androidrecruittest.repository.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawAlbum

interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushAlbums(albumList: List<RawAlbum>)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getAlbum(id: Int): RawAlbum
}