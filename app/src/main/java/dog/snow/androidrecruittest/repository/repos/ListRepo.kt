package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.ListDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.ui.model.ListItem
import retrofit2.Retrofit

class ListRepo(
    private val db: AppDatabase,
    private val listDao: ListDao){

    suspend fun getList() : List<ListItem> {
        return  listDao.getList()
    }

    suspend fun pushList(listList: ListItem){
        listDao.pushList(listList)
    }


}