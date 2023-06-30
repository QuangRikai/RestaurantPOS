package com.example.restaurantpos.ui.manager.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantpos.databinding.FragmentManagerCustomerBinding
import com.example.restaurantpos.db.entity.CustomerEntity

class ManagerCustomerFragment : Fragment() {
    lateinit var binding: FragmentManagerCustomerBinding
    lateinit var adapter: CustomerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerCustomerBinding.inflate(inflater, container, false)
        adapter = CustomerAdapter(requireParentFragment(), ArrayList(), object :
            CustomerAdapter.EventClickItemCustomerListener {
            override fun clickCustomer(itemCustomer: CustomerEntity) {
                TODO("Not yet implemented")
            }

        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}