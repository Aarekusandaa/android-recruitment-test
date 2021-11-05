package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.ListDao
import dog.snow.androidrecruittest.ui.model.ListItem

class ListRepo(private val listDao: ListDao){

    suspend fun getList() : List<ListItem> {
        return  listDao.getList()
    }

    suspend fun pushList(listList: List<ListItem>){
        listDao.pushList(listList)
    }


}