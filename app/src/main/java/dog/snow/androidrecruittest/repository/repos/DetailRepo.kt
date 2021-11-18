package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.DetailsDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.ui.model.Detail
import java.lang.Exception

class DetailRepo(
    /*private val db: AppDatabase,
    private val detailsDao: DetailsDao*/){

    suspend fun getDetails(detailsDao: DetailsDao, id: Int) : Detail{
        return detailsDao.getDetails(id)
    }

    suspend fun pushDetails(detailsDao: DetailsDao, detailsList: Detail){
        try {
            val insert = detailsDao.pushDetails(detailsList)
            if (insert < 0L){
                throw Exception()
            }
        }catch (e: Exception){
            println("Error DAO-> Detail")
        }
    }

}