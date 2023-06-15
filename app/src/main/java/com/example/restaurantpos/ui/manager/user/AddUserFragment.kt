package com.example.restaurantpos.ui.manager.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.databinding.FragmentAddUserBinding
import com.example.restaurantpos.db.entity.AccountEntity
import kotlinx.coroutines.NonDisposableHandle.parent

class AddUserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    lateinit var binding: FragmentAddUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var role = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // adapter for Spinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Receptionist", "Kitchen")
        )

        // Layout for All ROWs of Spinner. (Optional for ArrayAdapter).

        // Layout for All ROWs of Spinner. (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spnRole.adapter = adapter

        // When user select a list-Item
        binding.spnRole.setOnClickListener {
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    role = position + 1
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        binding.imgBack.setOnClickListener {
            onBack()
        }


        binding.txtAdd.setOnClickListener {
            viewModel.addUser(
                requireContext(), AccountEntity(
                    0,
                    binding.edtAddAccountName.toString().trim(),
                    binding.edtAddUserName.toString().trim(),
                    "123",
                    role,
                    true

                )
            )
            onBack()
        }


    }

    private fun onBack() {
        findNavController().popBackStack()
    }
}