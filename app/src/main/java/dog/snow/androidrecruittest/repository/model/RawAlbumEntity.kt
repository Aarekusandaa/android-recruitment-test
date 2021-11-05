package dog.snow.androidrecruittest.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Album")
data class RawAlbumEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String
) {
}