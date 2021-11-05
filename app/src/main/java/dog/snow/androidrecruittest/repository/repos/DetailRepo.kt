package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.DetailsDao
import dog.snow.androidrecruittest.ui.model.Detail

class DetailRepo(private val detailsDao: DetailsDao){

    suspend fun getDetails(id: Int) : Detail{
        return detailsDao.getDetails(id)
    }

    suspend fun pushDetails(detailsList: List<Detail>){
        detailsDao.pushDetails(detailsList)
    }

}