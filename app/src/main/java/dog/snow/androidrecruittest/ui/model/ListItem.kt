package dog.snow.androidrecruittest.ui.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "List")
@Parcelize
data class ListItem(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "albumTitle") val albumTitle: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String
) : Parcelable