package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.daos.ListDao
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.repos.ListRepo
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel() : ViewModel() {

    val list : MutableLiveData<List<ListItem>> = MutableLiveData(emptyList())
    private val listRepo = ListRepo()

    fun getList(listDao: ListDao){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val temp = listRepo.getList(listDao)
                withContext(Dispatchers.Main){
                    list.value = temp
                }
            }
        }
    }

    fun getSearchList(listDao: ListDao, text: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val temp = listRepo.getSearchList(listDao, text)
                withContext(Dispatchers.Main){
                    list.value = temp
                }
            }
        }

    }
}