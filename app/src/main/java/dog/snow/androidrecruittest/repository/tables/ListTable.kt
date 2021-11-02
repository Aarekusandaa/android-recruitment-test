package dog.snow.androidrecruittest.repository.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "List")
data class ListTable(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "album_title") val albumTitle: String,
    @ColumnInfo(name = "url") val thumbnailUrl: String
)
