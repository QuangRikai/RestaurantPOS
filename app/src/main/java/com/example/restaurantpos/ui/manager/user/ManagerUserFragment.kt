package com.example.restaurantpos.ui.manager.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.base.BaseFragment
import com.example.restaurantpos.databinding.FragmentManagerUserBinding
import com.example.restaurantpos.db.entity.AccountEntity

class ManagerUserFragment : BaseFragment<FragmentManagerUserBinding>(), ManagerUserAdapter.EventClickItemUserListener {

    private lateinit var viewModel: UserViewModel
    lateinit var adapter: ManagerUserAdapter
    override fun initCreate() {
        adapter = ManagerUserAdapter(requireContext(), ArrayList(), this)
        binding.rcyUserManagement.adapter = adapter

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //it là một biến ngầm định trong lambda expression, đại diện cho dữ liệu mới nhận được từ LiveData
        viewModel.getAllUser().observe(viewLifecycleOwner){
            adapter.setListData(it)
        }

        binding.imgAddUser.setOnClickListener {
            findNavController().navigate(R.id.action_mainManagerFragment_to_addUserFragment)
        }

    }

    override fun getInflateViewBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManagerUserBinding {
        return FragmentManagerUserBinding.inflate(layoutInflater, container, false)
    }

    override fun clickEditUser(itemUser: AccountEntity) {

    }
}