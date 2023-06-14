package com.example.restaurantpos.ui.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantpos.base.BaseActivity
import com.example.restaurantpos.databinding.ActivityLoginBinding
import com.example.restaurantpos.util.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var viewModel: LoginViewModel

    override fun initOnCreate() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.addFirstManager(this@LoginActivity)
        initListener()
    }


    private fun initListener() {
        binding.txtLogin.setOnClickListener {
            if (
                binding.edtUsername.text.toString().trim().isEmpty() ||
                binding.edtPassword.text.toString().trim().isEmpty()
            ) {
                CoroutineScope(Dispatchers.Main).launch {
                    showLoginInform()
                }
            } else {
                checkLogin(
                    binding.edtUsername.text.toString(),
                    binding.edtPassword.text.toString()
                )
            }
        }
    }
    private fun showLoginInform() {
        binding.txtInformLogin.text = "Username & password must not be empty!"
        binding.txtInformLogin.show()
    }

    private fun checkLogin(userName: String, password: String) {
        viewModel.checkLogin(this@LoginActivity, userName, password)
    }



    // End
    override fun getInflaterViewBinding(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}