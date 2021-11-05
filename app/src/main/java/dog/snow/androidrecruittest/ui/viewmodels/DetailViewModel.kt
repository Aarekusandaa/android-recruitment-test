package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.repos.DetailRepo
import dog.snow.androidrecruittest.ui.model.Detail
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepo: DetailRepo) : ViewModel() {

    private val _detail = MutableLiveData<Detail>()
    val detail: LiveData<Detail> = _detail

    init {
        viewModelScope.launch {
            //_detail.value = detailRepo.getDetails()
        }
    }
}