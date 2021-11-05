package dog.snow.androidrecruittest.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class RawUserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
) {
}