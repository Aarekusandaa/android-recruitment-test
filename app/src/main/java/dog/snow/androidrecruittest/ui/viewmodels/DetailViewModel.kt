package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.repos.DetailRepo
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepo: DetailRepo) : ViewModel() {

    var detail : MutableLiveData<Detail> = MutableLiveData()

    suspend fun getDetails(id: Int){
        detailRepo.getDetails(id)
    }
}