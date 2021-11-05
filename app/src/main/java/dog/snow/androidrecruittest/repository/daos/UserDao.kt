package dog.snow.androidrecruittest.repository.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawUser

interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushUsers(userList: List<RawUser>)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUser(id: Int): RawUser

    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<RawUser>

    @Delete
    suspend fun deleteUser(user: RawUser)
}