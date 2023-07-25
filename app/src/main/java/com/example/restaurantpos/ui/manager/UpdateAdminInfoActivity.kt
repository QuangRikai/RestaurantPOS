package com.example.restaurantpos.ui.manager

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantpos.databinding.ActivityUpdateAccountInfoBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.ui.main.MainManagerActivity
import com.example.restaurantpos.ui.manager.user.UserViewModel
import com.example.restaurantpos.util.Constant
import com.example.restaurantpos.util.DataUtil
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.hide
import com.example.restaurantpos.util.openActivity
import com.example.restaurantpos.util.show
import com.example.restaurantpos.util.showToast
import java.util.Calendar

class UpdateAdminInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateAccountInfoBinding
    private lateinit var viewModel: UserViewModel

    val calendar = Calendar.getInstance()
    val startYear = calendar.get(Calendar.YEAR) - 20
    val startMonth = calendar.get(Calendar.MONTH) - 5
    val startDay = calendar.get(Calendar.DAY_OF_MONTH) - 10
    lateinit var accountEntity: AccountEntity

    var name: String = ""

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

        binding.imgDate.setOnClickListener {
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val m = if (month < 10) {
                        "0" + (month + 1)
                    } else {
                        (month + 1).toString()
                    }

                    val dOfM = if (dayOfMonth < 10) {
                        "0" + (dayOfMonth)
                    } else {
                        (dayOfMonth).toString()
                    }

                    binding.txtBirthday.setText("$year/${m}/$dOfM")
                },
                startYear, startMonth, startDay
            ).show()
        }

        val accountId: Int = SharedPreferencesUtils.getAccountId()

        viewModel.getAccountById(accountId)
            .observe(this) { admin: MutableList<AccountEntity> ->
                if (admin.isNotEmpty()) {
                    binding.edtAdminName.setText(admin[0].account_name)
                    binding.txtBirthday.text = admin[0].account_birthday
                    binding.edtPhone.setText(admin[0].account_phone)
                    binding.edtUserName.setText(admin[0].user_name)

                    accountEntity = admin[0]
                }
            }

        /** Ràng buộc data */
        DataUtil.setEditTextWithoutSpecialCharacters(binding.edtAdminName, binding.txtError)
        DataUtil.setEditTextWithoutSpecialCharactersAndSpaces(binding.edtUserName, binding.txtError)
        DataUtil.setEditTextWithoutSpecialCharactersAndSpaces(binding.edtPassword, binding.txtError)


        binding.txtUpdate.setOnClickListener {
            binding.txtError.hide()
            val isNoEditUsername =
                accountEntity.user_name == binding.edtUserName.text.toString()
            name = accountEntity.user_name



            if (binding.edtAdminName.text.toString() != ""
                && binding.txtBirthday.text.toString() != ""
                && binding.edtPhone.text.toString() != ""
                && binding.edtUserName.text.toString() != ""
                && binding.edtPassword.text.toString() != ""
            )
            {
                if (binding.edtAdminName.text.length >= 3
                    && binding.edtUserName.text.length >= 3
                    && binding.edtPassword.text.length >= 3)
                {
                    if (binding.edtPhone.text.length >= 10)
                    {
                        // Main
                        accountEntity.account_name = binding.edtAdminName.text.trim().toString()
                        accountEntity.account_birthday = binding.txtBirthday.text.toString()
                        accountEntity.account_phone = binding.edtPhone.text.toString()
                        accountEntity.user_name = binding.edtUserName.text.toString()
                        accountEntity.password =
                            DataUtil.convertToMD5(binding.edtPassword.text.toString() + Constant.SECURITY_SALT)

                        viewModel.addUserAndCheckExistQ(
                            this@UpdateAdminInfoActivity,
                            accountEntity,
                            isNoEditUsername
                        )

                    }
                    else
                    {
                        binding.txtError.text = "Phone number \n needs to consist of 10 or 11 characters!"
                        binding.txtError.show()
                        binding.edtAdminName.clearFocus()
                        binding.edtPhone.clearFocus()
                        binding.edtUserName.clearFocus()
                        binding.edtPassword.clearFocus()
                    }

                }
                else
                {
                    binding.txtError.text = "Name & Login's username, password \n needs to consist of 3 to 14 characters!"
                    binding.txtError.show()
                    binding.edtAdminName.clearFocus()
                    binding.edtPhone.clearFocus()
                    binding.edtUserName.clearFocus()
                    binding.edtPassword.clearFocus()
                }

            }
            else
            {
                binding.txtError.text = "Information above must not be empty!"
                binding.txtError.show()
                binding.edtAdminName.clearFocus()
                binding.edtPhone.clearFocus()
                binding.edtUserName.clearFocus()
                binding.edtPassword.clearFocus()
            }

        }

        viewModel.isDuplicateQ
            .observe(this) {
                if (it) {
                    binding.txtError.text = "username already exists!"
                    binding.txtError.show()

                    accountEntity.user_name = name
                } else {
                    showToast("Account' information was updated successfully!")
                    SharedPreferencesUtils.setAccountName(accountEntity.account_name)
                    backToManager()
                }
            }
    }

    private fun backToManager() {
        openActivity(MainManagerActivity::class.java, true)
    }
}




