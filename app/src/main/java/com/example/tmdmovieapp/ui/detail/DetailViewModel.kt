package com.example.tmdmovieapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdmovieapp.model.MovieDetailResponse
import com.example.tmdmovieapp.network.ApiClient
import com.example.tmdmovieapp.util.Constants
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    val movieResponse: MutableLiveData<MovieDetailResponse> = MutableLiveData()

    // loading için live data
    val isLoading = MutableLiveData(false)

    // hata durumu için livedata
    val errorMessage: MutableLiveData<String?> = MutableLiveData()

    fun getMovieDetail(movieId: Int) { //Burada navigation ile gönderdiğimiz id'yi alıyoruz.
        isLoading.value = true
        viewModelScope.launch {
            try {
                //Servis isteğini atıyoruz
                val response = ApiClient.getClient()
                    .getMovieDetail(movieId = movieId.toString(), token = Constants.BEARER_TOKEN)
                if (response.isSuccessful) {
                    //Başarılı bir cevap alınırsa
                    movieResponse.postValue(response.body())
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