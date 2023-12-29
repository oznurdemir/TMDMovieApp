package com.example.tmdmovieapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tmdmovieapp.MainActivity
import com.example.tmdmovieapp.databinding.FragmentDetailBinding
import com.example.tmdmovieapp.util.loadImage

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.getMovieDetail(movieId = args.movieId)//viewmodel'deki oluşturduğumuz fonksiyonu çağırıyoruz.(servis isteğinin atılması için)

        // viewmodel'deki live datalaların gözlemlenme işlemlerini bu fonksiyon altında yapacağız
        observeEvents()

        return binding.root
    }

    private fun observeEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            // error mesajı görünür hale getir ve mesajı göster.(error string değer)
            binding.textViewDetailError.text = error
            binding.textViewDetailError.isVisible = true
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBarDetail.isVisible = loading
        }

        viewModel.movieResponse.observe(viewLifecycleOwner) { movie ->
            // response başarılı
            binding.imageViewDetail.loadImage(movie.backdropPath.toString())
            binding.textViewDetailRating.text = movie.voteAverage.toString()
            binding.textViewDetailStudio.text =
                movie.productionCompanies?.first()?.name //Listenin ilk elemanını alıyoruz
            binding.textViewDetailLanguage.text = movie.spokenLanguages?.first()?.englishName
            binding.textViewDetailOverview.text = movie.overview

            //viewlar gone durumunda olduğundan group'u kullanrak görününr yapıyoruz
            binding.viewGroup.isVisible = true

            //toolbar'da değişiklik yaptık
            (requireActivity() as MainActivity).supportActionBar?.title = movie.title
        }
    }

}