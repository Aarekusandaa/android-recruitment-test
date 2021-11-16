package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.daos.DetailsDao
import dog.snow.androidrecruittest.repository.repos.DetailRepo
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel() : ViewModel() {

    val detailRepo = DetailRepo()
    var detail : MutableLiveData<Detail> = MutableLiveData()

    fun getDetails(detailsDao: DetailsDao, id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val temp = detailRepo.getDetails(detailsDao, id)
                withContext(Dispatchers.Main){
                    detail.value = temp
                }
            }
        }

    }
}