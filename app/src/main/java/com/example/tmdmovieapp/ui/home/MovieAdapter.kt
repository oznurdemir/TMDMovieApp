package com.example.tmdmovieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdmovieapp.databinding.ItemHomeRecyclerViewBinding
import com.example.tmdmovieapp.model.MovieItem
import com.example.tmdmovieapp.util.loadCircleImage

//item'a basılınca seçilen filmin id'sini döndüren interface
interface MovieClickedListener {
    fun onMovieClicked(movieId : Int?)
}

class MovieAdapter(private val movieList: List<MovieItem?>,private val movieClickedListener : MovieClickedListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    //Recyclerview'daki öğeleri tutuyor, tekrar tekrar kullanılmasını sağlıyor.
    class ViewHolder(val binding: ItemHomeRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]

        //verileri ilgili yerlere çekiyoruz.
        holder.binding.textViewMovieTitle.text = movie?.title
        holder.binding.textViewOverview.text = movie?.overview
        holder.binding.textViewRating.text = movie?.voteAverage.toString()

        // Filmlerin posterleri için extension fonksiyon kullanıyoruz.
        holder.binding.imageViewMovie.loadCircleImage(movie?.posterPath.toString())

        //positionda gelen item tıklandığında
        holder.binding.root.setOnClickListener {
            movieClickedListener.onMovieClicked(movieId = movie?.id) // hangi filmi seçersek o filmin id'sini dönderecek
        }
    }
}