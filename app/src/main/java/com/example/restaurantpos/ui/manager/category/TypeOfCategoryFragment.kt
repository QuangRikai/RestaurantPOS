package com.example.restaurantpos.ui.manager.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantpos.databinding.FragmentTypeOfCategoryBinding
import com.example.restaurantpos.db.entity.CategoryEntity

class TypeOfCategoryFragment(position: Int, val category: CategoryEntity) : Fragment() {

    private lateinit var binding: FragmentTypeOfCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTypeOfCategoryBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}