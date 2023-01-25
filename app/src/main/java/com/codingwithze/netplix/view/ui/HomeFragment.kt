package com.codingwithze.netplix.view.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.codingwithze.netplix.R
import com.codingwithze.netplix.data.response.NowPlayingItem
import com.codingwithze.netplix.databinding.FragmentHomeBinding
import com.codingwithze.netplix.view.adapter.MovieAdapter
import com.codingwithze.netplix.view.adapter.PopularAdapter
import com.codingwithze.netplix.viewmodel.MovieViewModel
import com.codingwithze.netplix.viewmodel.MovieViewModel_Factory.newInstance
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    private lateinit var movieVM : MovieViewModel
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        getNowPlaying()
        getPopular()
        getTrendingMovie()
        getTrendingTv()

        binding.posterViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })
    }

    private val runnable = Runnable {
        binding.posterViewPager.currentItem = binding.posterViewPager.currentItem + 1
    }

    fun getNowPlaying(){
        movieVM.callGetNowPlayingMovie()
        movieVM.nowPlayingMovie.observe(viewLifecycleOwner){
            if (it != null){
                showNowPlaying(it)
            }
        }
    }

    fun showNowPlaying(data : List<NowPlayingItem>){
        val movieAdapter = MovieAdapter(data)
        binding.rvNowPlaying.adapter = movieAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNowPlaying.layoutManager = layoutManager

        movieAdapter.onClick = {
            val idNowPlaying = it.id
            val dialog = CustomDialog()
            dialog.arguments = Bundle().apply { putString("ID", idNowPlaying.toString()) }
            dialog.show(childFragmentManager, "customdialog")
        }
    }

    fun getTrendingMovie(){
        movieVM.callGetTrendingMovie()
        movieVM.trendingMovie.observe(viewLifecycleOwner){
            if (it != null){
                showTrendingMovie(it)
            }
        }
    }

    fun showTrendingMovie(data : List<NowPlayingItem>){
        val trendingAdapter = MovieAdapter(data)
        binding.rvTrendingMovie.adapter = trendingAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendingMovie.layoutManager = layoutManager

        trendingAdapter.onClick = {
            val idNowPlaying = it.id
            val dialog = CustomDialog()
            dialog.arguments = Bundle().apply { putString("ID", idNowPlaying.toString()) }
            dialog.show(childFragmentManager, "customdialog")
        }
    }

    fun getTrendingTv(){
        movieVM.callGetTrendingTv()
        movieVM.trendingTv.observe(viewLifecycleOwner){
            if (it != null){
                showTrendingTv(it)
            }
        }
    }
    fun showTrendingTv(data : List<NowPlayingItem>){
        val trendingAdapter = MovieAdapter(data)
        binding.rvTrendingTv.adapter = trendingAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendingTv.layoutManager = layoutManager

        trendingAdapter.onClick = {
            val idNowPlaying = it.id
            val dialog = CustomDialog()
            dialog.arguments = Bundle().apply { putString("ID", idNowPlaying.toString()) }
            dialog.show(childFragmentManager, "customdialog")
        }
    }



   fun  getPopular(){
      handler = Handler(Looper.myLooper()!!)
       showLoading(true)
       movieVM.callGetPopularNetplix()
       movieVM.popularNetplix.observe(viewLifecycleOwner){
           if (it != null){
               showPopular(it)
           }
       }
    }

    fun showPopular(data : List<NowPlayingItem>){
        val popularAdapter = PopularAdapter(data, binding.posterViewPager)
        binding.posterViewPager.adapter = popularAdapter

        binding.posterViewPager.offscreenPageLimit = 2
        binding.posterViewPager.clipToPadding =  false
        binding.posterViewPager.clipChildren = false
        binding.posterViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        popularAdapter.onClick = {
            val idNowPlaying = it.id
            val dialog = CustomDialog()
            dialog.arguments = Bundle().apply { putString("ID", idNowPlaying.toString()) }
            dialog.show(childFragmentManager, "customdialog")
        }
    }

    private fun showLoading(isLoading : Boolean){
        if(isLoading){
            binding.shimmerLayout.startShimmerAnimation()
        }else{
            binding.shimmerLayout.stopShimmerAnimation()
            binding.shimmerLayout.visibility = View.GONE
        }
    }


}