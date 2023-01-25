package com.codingwithze.netplix.view.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.codingwithze.netplix.R
import com.codingwithze.netplix.databinding.CustomDialogBinding
import com.codingwithze.netplix.viewmodel.MovieViewModel
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.google.android.youtube.player.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class CustomDialog : DialogFragment() {

    lateinit var binding : CustomDialogBinding
    lateinit var _ID : String
    lateinit var movieViewModel : MovieViewModel

    val VIDEO_ID = "VEuAbbxflXk"
    val YOUTUBE_API_KEY ="AIzaSyCXb8KXyVk3f69IZbHIFBamIOl0YC_Mk6U"
    private val videoFile = "https://youtu.be/uRu3zLOJN2c"
    private var mPlayer : SimpleExoPlayer?= null
    private var mCurrentMilis : Long? = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = CustomDialogBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        val idNow = arguments?.getString("ID")
        _ID = idNow!!

        binding.btnClose.setOnClickListener{
            dismiss()
        }

        getMovieDetail()
        getMovieVideo()

    }

    fun getMovieDetail(){

        movieViewModel.callGetMovieDetail(_ID.toInt())
        movieViewModel.movieDetail.observe(viewLifecycleOwner){
            if (it != null){
                binding.titleMovie.text = it.title
                binding.overviewMovie.text = it.overview
                binding.genreMovie.text = it.genres.get(0).name
                binding.starMovie.text = round(it.voteAverage).toString()  + "/10"
                binding.durationMovie.text = it.runtime.toString() + " minutes"
                binding.popularityMovie.text = it.popularity.toString() + " watched"
            }
        }
    }

    fun getMovieVideo(){
        movieViewModel.callGetMovieVideo(_ID.toInt())
        movieViewModel.movieVideo.observe(viewLifecycleOwner){
            if (it != null){
                val KEY = it.results.get(0).key
                playEmbedVideo(KEY)
            }
        }
    }

    fun playEmbedVideo(movieid : String) {
        mPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), DefaultRenderersFactory(requireContext()), DefaultTrackSelector(requireContext()))
        binding.embedVideo.player = mPlayer
        binding.embedVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT

        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), Util.getUserAgent(requireContext(), "player"))
        val extractorMediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(
            Uri.parse("https://www.youtube.com/watch?v=$movieid"))

        val isResuming = mCurrentMilis !=0L
        mPlayer?.prepare(extractorMediaSource, isResuming, false)
        mPlayer?.playWhenReady = true
        if(isResuming){
            mPlayer?.seekTo(mCurrentMilis!!)
        }
    }

    private fun releasePlayer(){
        if(mPlayer == null){
            return
        }
        mCurrentMilis = mPlayer?.currentPosition
        mPlayer?.release()
        mPlayer = null
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }




}