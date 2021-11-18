package dog.snow.androidrecruittest.repository.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.ui.model.ListItem

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushList(listList: ListItem) : Long

    @Query("SELECT * FROM List")
    suspend fun getList(): List<ListItem>

    @Query("SELECT * FROM List WHERE title LIKE '%' || :text || '%' OR albumTitle LIKE '%' || :text || '%'")
    suspend fun getSearchList(text: String): List<ListItem>
}