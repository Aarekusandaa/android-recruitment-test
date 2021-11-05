package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.DetailsDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.ui.model.Detail

class DetailRepo(
    private val db: AppDatabase,
    private val detailsDao: DetailsDao){

    suspend fun getDetails(id: Int) : Detail{
        return detailsDao.getDetails(id)
    }

    suspend fun pushDetails(detailsList: Detail){
        detailsDao.pushDetails(detailsList)
    }

}