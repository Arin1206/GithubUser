package com.example.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.data.retrofit.ApiService
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.gthubuserapi.DetailUserActivity
import com.example.githubuser.ui.gthubuserapi.GithubAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: GithubAdapter
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiConfig.getApiService()


        binding.rvFav.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }


        adapter = GithubAdapter()


        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


        binding.rvFav.adapter = adapter



        viewModel.getfavoriteuser()?.observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarurl, id = it.id)
                items.add(item)
            }
            adapter.submitList(items)
        }


        adapter.onClickListener = View.OnClickListener { view ->
            val position = binding.rvFav.getChildAdapterPosition(view)
            if (position != RecyclerView.NO_POSITION) {
                val item = adapter.currentList[position]
                onItemClick(item)
            }
        }
    }


    private fun onItemClick(item: ItemsItem) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra("username", item.login)
        intent.putExtra("id", item.id)
        intent.putExtra("avatarurl", item.avatarUrl)
        startActivity(intent)
    }
}

