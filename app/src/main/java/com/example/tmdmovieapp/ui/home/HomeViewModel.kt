package com.example.tmdmovieapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdmovieapp.model.MovieItem
import com.example.tmdmovieapp.network.ApiClient
import com.example.tmdmovieapp.util.Constants
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //Live Data Oluşturma
    // movie list live data
    val movieList: MutableLiveData<List<MovieItem?>?> = MutableLiveData()

    // loading için live data
    val isLoading = MutableLiveData(false)

    // hata durumu için livedata
    val errorMessage: MutableLiveData<String?> = MutableLiveData()

    init {
        //Uygulama çalıştığında film listesi direkt gelsin
        getMovieList()
    }

    fun getMovieList() {
        isLoading.value = true

        //api service'te coroutine func oluşturduğumuz için
        viewModelScope.launch {
            try {
                // servis isteği oluşturma
                val response = ApiClient.getClient().getMovieList(token = Constants.BEARER_TOKEN)
                if (response.isSuccessful) {
                    movieList.postValue(response.body()?.movieItems)
                } else {
                    if (response.message().isNullOrEmpty()) {
                        errorMessage.value = "An unknown error occured"
                    } else {
                        errorMessage.value = response.message()
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}