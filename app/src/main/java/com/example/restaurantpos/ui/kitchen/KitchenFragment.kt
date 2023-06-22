package com.example.restaurantpos.ui.kitchen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentKitchenBinding
import com.example.restaurantpos.databinding.FragmentOrderedTableBinding

class KitchenFragment : Fragment() {

    lateinit var binding: FragmentKitchenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitchenBinding.inflate(inflater, container, false)
        return binding.root
    }
}