package dog.snow.androidrecruittest.repository.daos

import androidx.room.*
import dog.snow.androidrecruittest.repository.model.RawUserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushUsers(userList: List<RawUserEntity>)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUser(id: Int): RawUserEntity

    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<RawUserEntity>

    @Delete
    suspend fun deleteUser(user: RawUserEntity)
}