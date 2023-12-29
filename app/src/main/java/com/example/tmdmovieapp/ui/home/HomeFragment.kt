package com.example.tmdmovieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tmdmovieapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieAdapter: MovieAdapter

    // viewmodel tanımlama
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // servis isteği atma
        // viewmodelde yapıyoruz artık
        //viewModel.getMovieList()

        // viewmodel'deki live datalaların gözlemlenme işlemlerini bu fonksiyon altında yapacağız
        observeEvents()

        return binding.root
    }

    private fun observeEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner){error->
            // error mesajı görünür hale getir ve mesajı göster
            binding.homeErrorText.text = error
            binding.homeErrorText.isVisible = true
        }

        viewModel.isLoading.observe(viewLifecycleOwner){loading->
            binding.progressBar.isVisible = loading
        }

        viewModel.movieList.observe(viewLifecycleOwner){list->
            if(list.isNullOrEmpty()){
                binding.homeErrorText.text = "There is any movie"
                binding.homeErrorText.isVisible = true
            }else{
                movieAdapter = MovieAdapter(list, object : MovieClickedListener {
                    override fun onMovieClicked(movieId: Int?) {
                        movieId?.let {
                            // id boş dönmezse detay sayfasına gidilecek
                            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                            findNavController().navigate(action)

                        }
                    }

                }) //İstekten dönen listeyi adapter'a yolluyoruz.
                binding.homeRecycleView.adapter = movieAdapter
            }
        }
    }


}