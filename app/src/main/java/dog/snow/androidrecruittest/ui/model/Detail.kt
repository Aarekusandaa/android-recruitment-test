package dog.snow.androidrecruittest.ui.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Details")
@Parcelize
data class Detail(
    @PrimaryKey @ColumnInfo(name = "id") val photoId: Int,
    @ColumnInfo(name = "photoTitle") val photoTitle: String,
    @ColumnInfo(name = "albumTitle") val albumTitle: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "url") val url: String
) : Parcelable