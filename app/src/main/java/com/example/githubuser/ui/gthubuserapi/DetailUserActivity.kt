package com.example.githubuser.ui.gthubuserapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: ViewModel

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_following,
        R.string.tab_follower
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: ""
        val id = intent.getIntExtra("id", 0)
        val avatarurl = intent.getStringExtra("avatarurl") ?: ""


        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.getUserDetail(username).observe(this, { userDetail ->
            populateUI(userDetail)
        })


        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toogleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toogleFavorite.isChecked = false
                        _isChecked = false
                    }

                }
            }
        }

        binding.toogleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.insert(username, id, avatarurl)
            } else {
                viewModel.remove(id)
            }
            binding.toogleFavorite.isChecked = _isChecked
        }


        val adapter = SectionsPagerAdapter(this, username)
        adapter.username = username
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Following"
                1 -> "Follower"
                else -> ""
            }
        }.attach()

        showLoading()


    }


    private fun populateUI(userDetail: DetailUserResponse?) {
        if (userDetail != null) {
            binding.namauser.text = userDetail.login
            binding.follower.text = getString(R.string.follower, userDetail.followers)
            binding.following.text = getString(R.string.following, userDetail.following)
            binding.nama.text = userDetail.name
            Glide.with(this)
                .load(userDetail.avatarUrl)
                .into(binding.imgItemPhoto)
        }


        hideLoading()
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}
