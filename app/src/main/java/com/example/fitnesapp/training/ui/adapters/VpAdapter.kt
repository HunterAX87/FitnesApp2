package com.example.fitnesapp.training.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitnesapp.training.ui.fragments.DaysFragment
import com.example.fitnesapp.training.utils.TrainingUtils

class VpAdapter(fragment: Fragment, private val custom: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TrainingUtils.difListType.size - custom
    }

    override fun createFragment(position: Int): Fragment {
        return DaysFragment.newInstance()
    }
}