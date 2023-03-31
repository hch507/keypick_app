package com.keyword.keyword_miner.KeywordSearch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPageAdapter(val fragmnetList:List<Fragment>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragmnetList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmnetList.get(position)
    }
}
