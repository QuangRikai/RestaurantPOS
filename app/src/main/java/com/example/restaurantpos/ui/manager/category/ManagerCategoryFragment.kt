package com.example.restaurantpos.ui.manager.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentManagerCategoryBinding

class ManagerCategoryFragment : Fragment() {

    lateinit var binding: FragmentManagerCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerCategoryBinding.inflate(inflater, container, false)
        return binding.root


    }
}