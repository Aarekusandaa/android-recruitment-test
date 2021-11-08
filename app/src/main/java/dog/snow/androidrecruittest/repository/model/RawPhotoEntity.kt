package dog.snow.androidrecruittest.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
class RawPhotoEntity (
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "albumId") val albumId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String){
}