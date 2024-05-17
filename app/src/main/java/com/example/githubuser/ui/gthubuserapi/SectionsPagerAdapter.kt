package com.example.githubuser.ui.gthubuserapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, username: String) :
    FragmentStateAdapter(activity) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FragmentGithub()
        fragment.arguments = Bundle().apply {
            putInt(FragmentGithub.ARG_POSITION, position + 1)
            putString(FragmentGithub.ARG_USERNAME, username)
        }


        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}