package dog.snow.androidrecruittest.repository.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.tables.ListTable
import dog.snow.androidrecruittest.ui.model.ListItem

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushList(listList: List<ListItem>)

    @Query("SELECT * FROM List")
    suspend fun getList(): ListItem
}