package com.codingwithze.netplix.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.codingwithze.netplix.data.response.NowPlayingItem
import com.codingwithze.netplix.databinding.ItemMovieBinding

class PopularAdapter(private val listPopular : List<NowPlayingItem>, private val viewPager2 : ViewPager2):RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    var onClick : ((NowPlayingItem) -> Unit)? = null
    class ViewHolder(var binding : ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var source = listPopular[position]
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w400${source.posterPath}")
            .into(holder.binding.ivMoviePoster)
        holder.binding.ivMoviePoster.setOnClickListener{
            onClick?.invoke(source)
        }
        if (position == listPopular.size - 1){
            viewPager2.post(runnable)
        }
    }

    private val runnable = Runnable{
        listPopular.plus(listPopular)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listPopular.size
    }
}