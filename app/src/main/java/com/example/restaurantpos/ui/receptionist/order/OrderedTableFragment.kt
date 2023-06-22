package com.example.restaurantpos.ui.receptionist.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantpos.databinding.FragmentOrderedTableBinding


class OrderedTableFragment : Fragment() {

    lateinit var binding: FragmentOrderedTableBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderedTableBinding.inflate(inflater, container, false)
        return binding.root
    }


}