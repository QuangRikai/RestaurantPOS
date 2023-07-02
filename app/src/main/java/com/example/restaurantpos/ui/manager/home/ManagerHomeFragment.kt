package com.example.restaurantpos.ui.manager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentManagerHomeBinding

class ManagerHomeFragment : Fragment() {

    lateinit var binding: FragmentManagerHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Shift */
        binding.btnShift.setOnClickListener {
            findNavController().navigate(R.id.action_mainManagerFragment_to_shiftFragment)
        }

        /** Statistic */
        binding.txtStatistic.setOnClickListener {
            findNavController().navigate(R.id.action_mainManagerFragment_to_statisticFragment)
        }


    }
}