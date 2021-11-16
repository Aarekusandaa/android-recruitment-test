package dog.snow.androidrecruittest.repository.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.ui.model.Detail

@Dao
interface DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushDetails(detailsList: Detail)

    @Query("SELECT * FROM Details WHERE id = :id")
    suspend fun getDetails(id: Int): Detail
}