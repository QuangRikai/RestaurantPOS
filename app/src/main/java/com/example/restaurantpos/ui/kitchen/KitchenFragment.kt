package com.example.restaurantpos.ui.kitchen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantpos.databinding.FragmentKitchenBinding

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