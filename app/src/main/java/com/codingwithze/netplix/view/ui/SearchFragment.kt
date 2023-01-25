package com.codingwithze.netplix.view.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithze.netplix.data.response.NowPlayingItem
import com.codingwithze.netplix.databinding.FragmentSearchBinding
import com.codingwithze.netplix.view.adapter.MovieAdapter
import com.codingwithze.netplix.viewmodel.MovieViewModel
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    lateinit var binding : FragmentSearchBinding
    private lateinit var movieVM : MovieViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
//        val aa = binding.searchNetplix.queryHint
//        binding.txtSearch.text = aa
        binding.btnSearch.setOnClickListener{
            val titleSearch = binding.etSearch.text.toString()
            getSearch(titleSearch)
        }

    }

    fun getSearch(title:String){
        if (binding.etSearch.text.toString().isNotEmpty()){
            movieVM.callGetSearchNetplix(title)
            movieVM.searchNetplix.observe(viewLifecycleOwner){
                if (it != null){
                    showSearchNetplix(it)
                }
            }
        }
    }

    fun showSearchNetplix(data : List<NowPlayingItem>){
        val searchAdapter = MovieAdapter(data)
        binding.rvSearch.adapter=searchAdapter
        val layoutManager = GridLayoutManager(context,2)
        binding.rvSearch.layoutManager = layoutManager

        searchAdapter.onClick = {
            val idNowPlaying = it.id
            val dialog = CustomDialog()
            dialog.arguments = Bundle().apply { putString("ID", idNowPlaying.toString()) }
            dialog.show(childFragmentManager, "customdialog")
        }
    }





}