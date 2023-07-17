package com.example.restaurantpos.ui.manager.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.databinding.FragmentAddUserBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.util.DataUtil
import com.example.restaurantpos.util.show

class AddUserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    lateinit var binding: FragmentAddUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun showMessage(content: String) {
        Toast.makeText(requireContext(), content, Toast.LENGTH_SHORT).show()
    }

    /** Xử lý role trong spinner */
    private var role = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**  Xử lý role trong spinner  */
        handleUserRoleBySpinner()

        /**  Khai báo viewModel --> Dùng phương thức addUser --> set ADD Button */
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.isDuplicate.observe(viewLifecycleOwner) {
            if (it) showMessage("This account (username) is existing!")
            else onBack()
        }

        /** Device's Back Button*/
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        /** Cancel/Back */
        binding.txtCancel.setOnClickListener {
            onBack()
        }

        binding.imgBack.setOnClickListener {
            onBack()
        }
        /** ADD Button*/
        binding.txtAdd.setOnClickListener {
            if (binding.edtAddUserName.text.isEmpty() || binding.edtAddAccountName.text.isEmpty()) {
                binding.txtError.show()
            } else {
                viewModel.addUser(
                    requireActivity(), AccountEntity(
                        0,
                        binding.edtAddAccountName.text.toString().trim(),
                        "",
                        "",
                        binding.edtAddUserName.text.toString().trim(),
                        DataUtil.convertToMD5("123"),
                        role,
                        true
                    )
                )
//                onBack()
            }
        }
    }

    private fun handleUserRoleBySpinner() {
        val listUserRole = listOf("Receptionist", "Kitchen")
        binding.spnRole.adapter = UserRoleSpinnerAdapter(requireActivity(), listUserRole)
        binding.spnRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                role = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun onBack() {
        findNavController().popBackStack()
    }
}