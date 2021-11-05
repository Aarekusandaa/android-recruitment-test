package dog.snow.androidrecruittest.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
class RawPhotoEntity (
    @PrimaryKey val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String){
}