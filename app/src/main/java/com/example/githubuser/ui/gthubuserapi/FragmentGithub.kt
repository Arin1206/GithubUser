package com.example.githubuser.ui.gthubuserapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.FragmentGithubBinding

class FragmentGithub : Fragment() {

    private lateinit var binding: FragmentGithubBinding
    private lateinit var viewModel: ViewModelFollower
    private var position: Int? = null
    private var username: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGithubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)

            viewModel = ViewModelProvider(this).get(ViewModelFollower::class.java)

            recyclerView = binding.github2
            recyclerView.layoutManager = LinearLayoutManager(context)

            showLoading()

            if (position == 1) {

                viewModel.getFollowing(username)
                    .observe(viewLifecycleOwner, Observer { followingList ->
                        adapter = FollowerAdapter()
                        adapter.updateData(followingList)
                        recyclerView.adapter = adapter
                        hideLoading()
                    })

            } else {

                viewModel.getFollowers(username)
                    .observe(viewLifecycleOwner, Observer { followerList ->
                        adapter = FollowerAdapter()
                        adapter.updateData(followerList)
                        recyclerView.adapter = adapter
                        hideLoading()
                    })


            }

        }
    }

    private fun showLoading() {

        binding.progressBar.visibility =
            View.VISIBLE
    }

    private fun hideLoading() {

        binding.progressBar.visibility = View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }


}
