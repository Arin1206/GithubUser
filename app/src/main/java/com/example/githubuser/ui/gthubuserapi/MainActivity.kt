package com.example.githubuser.ui.gthubuserapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.response.Response
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.data.retrofit.ApiService
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.ui.darkmode.SettingActivity
import com.example.githubuser.ui.darkmode.SettingPreferences
import com.example.githubuser.ui.darkmode.dataStore
import com.example.githubuser.ui.favorite.FavoriteActivity
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService
    private val TAG: String = "MainActivity"
    private lateinit var toolbar: Toolbar
    private lateinit var settingPreferences: SettingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingPreferences = SettingPreferences.getInstance(applicationContext.dataStore)

        lifecycleScope.launchWhenStarted {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        binding.github.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.github.addItemDecoration(itemDecoration)


        apiService = ApiConfig.getApiService()
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val query = searchView.text.toString()
                    searchGithub(query)
                    searchView.hide()
                    true
                }
        }




        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.love -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu1 -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }


    }

    override fun onStart() {
        super.onStart()
        getDataFromApi()

    }


    private fun onItemClick(item: ItemsItem) {
        showLoading(true)
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra("username", item.login)
        intent.putExtra("id", item.id)
        intent.putExtra("avatarurl", item.avatarUrl)
        startActivity(intent)
        showLoading(false)
    }


    private fun searchGithub(query: String) {

        showLoading(true)
        val call = apiService.getGithub(query)
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val data = response.body()?.items ?: emptyList()
                    val adapter = GithubAdapter()
                    binding.github.adapter = adapter
                    adapter.submitList(data)
                    adapter.onClickListener = View.OnClickListener { view ->
                        val position = binding.github.getChildAdapterPosition(view)
                        if (position != RecyclerView.NO_POSITION) {
                            val item = adapter.currentList[position]
                            onItemClick(item)
                        }
                    }
                    showLoading(false)
                } else {
                    // Handle error response
                    printLog("Error: ${response.code()} ${response.message()}")
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                // Handle network failure
                printLog("Network error: ${t.message}")
            }
        })
    }


    private fun getDataFromApi() {

        showLoading(true)
        val query = "Arif"
        val oke = apiService.getGithub(query)
        oke.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val data =
                        response.body()?.items ?: emptyList()
                    val adapter = GithubAdapter()
                    binding.github.adapter = adapter  // Set adapter ke RecyclerView
                    adapter.submitList(data)  // Kirim data ke adapter
                    showLoading(false)  // Sembunyikan progress bar (opsional)
                    adapter.onClickListener = View.OnClickListener { view ->
                        val position = binding.github.getChildAdapterPosition(view)
                        if (position != RecyclerView.NO_POSITION) {
                            val item = adapter.currentList[position]
                            onItemClick(item) // Call your existing onItemClick function
                        }
                    }
                } else {
                    // Handle error response
                    printLog("Error: ${response.code()} ${response.message()}")
                    showLoading(false)  // Sembunyikan progress bar (opsional)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {

                printLog("Network error: ${t.message}")

            }

        })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}
