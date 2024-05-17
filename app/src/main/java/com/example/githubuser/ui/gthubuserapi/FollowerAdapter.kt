package com.example.githubuser.ui.gthubuserapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.databinding.Item2Binding

class FollowerAdapter :
    ListAdapter<DetailUserResponse, FollowerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = Item2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
    }

    fun updateData(newList: List<DetailUserResponse>) {
        submitList(newList)
    }

    class MyViewHolder(private val binding: Item2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follow: DetailUserResponse) {
            binding.tvOke.text = follow.login
            val imageUrl = follow.avatarUrl
            Glide.with(itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgOke)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailUserResponse>() {
            override fun areItemsTheSame(
                oldItem: DetailUserResponse,
                newItem: DetailUserResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DetailUserResponse,
                newItem: DetailUserResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
