package com.example.musicplayer.helpers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.data.database.Song
import com.example.musicplayer.databinding.SongListItemBinding
import com.example.musicplayer.presentation.MainActivity
import com.squareup.picasso.Picasso

class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongItemViewHolder>() {
    private var songList = listOf<Song>()

    inner class SongItemViewHolder(binding: SongListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var nameTextView: TextView? = null
        var performerTextView: TextView? = null
        var coverImageView: ImageView? = null
        var imageButton: ImageButton? = null

        init {
            nameTextView = binding.name
            performerTextView = binding.performer
            coverImageView = binding.cover
            imageButton = binding.likeButton
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
        return SongItemViewHolder(
            SongListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        holder.nameTextView?.text = songList[position].name
        holder.performerTextView?.text = songList[position].performer

        Picasso.get()
            .load(songList[position].coverURL)
            .into(holder.coverImageView)

        holder.coverImageView?.rootView?.setOnClickListener {
            val activity = (it.context as MainActivity)
            activity.playerViewModel.setCurrentSong(songList[position])
            activity.navController
                .navigate(R.id.playerFragment)
        }

        holder.imageButton?.setOnClickListener {
            val activity = (it.context as MainActivity)

            val newSongInfo = songList[position]
            newSongInfo.isFavorite = !newSongInfo.isFavorite

            activity.playerViewModel.updateSong(newSongInfo)
        }

        holder.coverImageView?.rootView?.setOnLongClickListener {
            val activity = (it.context as MainActivity)
            activity.playerViewModel.deleteSong(songList[position])
            true
        }

        if (songList[position].isFavorite) {
            holder.imageButton?.setImageResource(R.drawable.heart_pressed)
        } else {
            holder.imageButton?.setImageResource(R.drawable.heart)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSongList(newList: List<Song>) {
        songList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = songList.size
}
