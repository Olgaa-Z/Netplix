package com.codingwithze.netplix.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingwithze.netplix.data.response.NowPlayingItem
import com.codingwithze.netplix.databinding.ItemNowPlayingBinding

class MovieAdapter(var listMovie : List<NowPlayingItem>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    var onClick : ((NowPlayingItem) -> Unit)? = null
    class ViewHolder(var binding : ItemNowPlayingBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var source = listMovie[position]
        holder.binding.titleMovie.text = source.title
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w400${source.posterPath}")
            .into(holder.binding.imgMovie)
        holder.binding.areaDetail.setOnClickListener{
            onClick?.invoke(source)
        }
        holder.binding.rateMovie.text = source.voteAverage.toString()
    }

    override fun getItemCount(): Int {
        return  listMovie.size
    }
}