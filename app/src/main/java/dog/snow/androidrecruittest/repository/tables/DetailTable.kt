package dog.snow.androidrecruittest.repository.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Details")
data class DetailTable(
    @PrimaryKey @ColumnInfo(name = "id") val photoId: Int,
    @ColumnInfo(name = "photo_title") val photoTitle: String,
    @ColumnInfo(name = "album_title") val albumTitle: String,
    @ColumnInfo(name = "name") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "url") val url: String
)
