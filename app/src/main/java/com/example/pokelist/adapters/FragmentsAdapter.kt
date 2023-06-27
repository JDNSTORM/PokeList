package com.example.pokelist.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pokelist.ui.fragments.BaseFragment

class FragmentsAdapter(manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle) {
    private val fragments: ArrayList<BaseFragment<*>> = ArrayList()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun fragments(): List<BaseFragment<*>> = fragments
    fun addFragment(vararg newFragments: BaseFragment<*>){
        fragments.addAll(newFragments)
    }

    /**
     * Trial
     */
    fun <T: BaseFragment<*>> removeFragment(fragment: T){
        val fragmentToRemove = fragments.find { it::class == fragment::class }
        fragmentToRemove?.also {
            fragments.remove(it)
        }
    }
}