package com.example.imagesearch.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imagesearch.presentation.fragment.LikeFragment
import com.example.imagesearch.presentation.fragment.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    val fragments : List<Fragment> = listOf(SearchFragment(), LikeFragment())

    override fun getItemCount(): Int {
        return  fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}