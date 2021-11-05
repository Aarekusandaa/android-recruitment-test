package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.repos.ListRepo
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.launch

class ListViewModel(private val listRepo: ListRepo) : ViewModel() {

    var list : MutableLiveData<List<ListItem>> = MutableLiveData()

    suspend fun getList(){
        listRepo.getList()
    }
}