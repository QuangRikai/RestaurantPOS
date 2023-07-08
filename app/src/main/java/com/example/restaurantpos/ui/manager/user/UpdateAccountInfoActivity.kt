package com.example.restaurantpos.ui.manager.user

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantpos.databinding.ActivityUpdateAccountInfoBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.ui.main.MainManagerActivity
import com.example.restaurantpos.util.DataUtil
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity

class UpdateAccountInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateAccountInfoBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /** Khai báo viewModel --> Dùng phương thức addUser --> set ADD Button */
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        /** Device's Back Button*/
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backToManager()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)

        /** Cancel/Back */
        binding.txtCancel.setOnClickListener {
            backToManager()
        }
        binding.imgBack.setOnClickListener {
            backToManager()
        }

        val accountId: Int = SharedPreferencesUtils.getAccountId()
        viewModel.getAccountById(accountId)
            .observe(this) { admin: MutableList<AccountEntity> ->
                binding.edtAdminName.hint = admin[0].account_name
                binding.edtUserName.hint = admin[0].user_name
                /** Update Button */
                binding.txtUpdate.setOnClickListener {
                    if (binding.edtAdminName.text.toString() != "") {
                        admin[0].account_name = binding.edtAdminName.text.toString()
                    }

                    if (binding.edtUserName.text.toString() != "") {
                        admin[0].user_name = binding.edtUserName.text.toString()
                    }

                    if (binding.edtPassword.text.toString() != "") {
                        admin[0].password = DataUtil.convertToMD5(binding.edtPassword.text.toString())
                    }

                    viewModel.addUser(this, admin[0])
                    backToManager()
                }
            }
    }

    private fun backToManager() {
        openActivity(MainManagerActivity::class.java)
    }
}




