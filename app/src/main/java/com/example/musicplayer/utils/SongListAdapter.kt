package com.example.musicplayer.utils

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.SongListItemBinding
import com.example.musicplayer.domain.entities.Track
import com.example.musicplayer.presentation.MainActivity
import com.squareup.picasso.Picasso

class SongListAdapter(fragment: Fragment) :
    RecyclerView.Adapter<SongListAdapter.SongItemViewHolder>() {
    private var songList = listOf<Track>()
    private val activity = (fragment.activity as MainActivity)

    inner class SongItemViewHolder(binding: SongListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var nameTextView: TextView = binding.name
        var performerTextView: TextView = binding.performer
        var coverImageView: ImageView = binding.cover
        var likeButton: ImageButton = binding.likeButton
        var menuButton: ImageButton = binding.menuButton
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
        holder.nameTextView.text = songList[position].name
        holder.performerTextView.text = songList[position].performer

        configurePopupMenu(holder.menuButton, songList[position])

        Picasso.get()
            .load(songList[position].coverURL)
            .into(holder.coverImageView)

        holder.coverImageView.rootView.setOnClickListener {
            activity.playerViewModel.setCurrentSong(songList[position])
            activity.navController
                .navigate(R.id.playerFragment)
        }

        holder.likeButton.setOnClickListener {
            val newSongInfo = songList[position]
            newSongInfo.isFavorite = !newSongInfo.isFavorite
            activity.playerViewModel.updateSong(newSongInfo)
        }

        if (songList[position].isFavorite) {
            holder.likeButton.setImageResource(R.drawable.heart_pressed)
        } else {
            holder.likeButton.setImageResource(R.drawable.heart)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSongList(newList: List<Track>) {
        songList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = songList.size

    private fun configurePopupMenu(menuButton: ImageButton, track: Track) {
        val popupMenu = PopupMenu(activity, menuButton)
        popupMenu.inflate(R.menu.song_popup)
        popupMenu.gravity = Gravity.END

        menuButton.setOnClickListener {
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteTrack -> {
                    activity.playerViewModel.deleteSong(track)
                }
            }
            false
        }
    }
}
