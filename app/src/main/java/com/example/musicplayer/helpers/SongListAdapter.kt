package com.example.musicplayer.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.SongListItemBinding
import com.example.musicplayer.domain.Song
import com.example.musicplayer.presentation.MainActivity

class SongListAdapter(
    private val songList: List<Song>,
) :
    RecyclerView.Adapter<SongListAdapter.SongItemViewHolder>() {

    inner class SongItemViewHolder(binding: SongListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var nameTextView: TextView? = null
        var performerTextView: TextView? = null
        var coverImageView: ImageView? = null

        init {
            nameTextView = binding.name
            performerTextView = binding.performer
            coverImageView = binding.cover
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
        val id = holder.itemView.resources.getIdentifier(
            "@drawable/${songList[position].coverPath}",
            null,
            holder.itemView.context.packageName
        )
        holder.coverImageView?.setImageResource(id)

        holder.coverImageView?.rootView?.setOnClickListener {
            val activity = (it.context as MainActivity)
            activity.playerViewModel.currentSongData
                .postValue(songList[position])
            activity.navController
                .navigate(R.id.action_mainScreenFragment_to_playerFragment)
        }
    }

    override fun getItemCount() = songList.size
}
