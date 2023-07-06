package com.example.restaurantpos.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantpos.base.BaseActivity
import com.example.restaurantpos.databinding.ActivitySplashBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.login.LoginActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    lateinit var viewModel: SplashViewModel
    private val DELAY_TIME = 1500L


    override fun initOnCreate() {
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        /** Set Data for DB */
//        createDataFirst()
        /** Start Login Screen */
        startLoginActivity()
    }

    private fun createDataFirst() {
        /** 1. Account */
        viewModel.addListAccount(
            listOf(
                AccountEntity(1, "Quang Test Manager", "quang0", "123", 0, true),
                AccountEntity(2, "Quang Test Receptionist", "quang1", "123", 1, true),
                AccountEntity(3, "Quang Test Kitchen", "quang2", "123", 2, true),
            )
        )

        /** 2. Table */
        viewModel.addTable(this, TableEntity(1, "Table 01", 0))
        viewModel.addTable(this, TableEntity(2, "Table 02", 0))
        viewModel.addTable(this, TableEntity(3, "Table 03", 0))
        viewModel.addTable(this, TableEntity(4, "Table 04", 0))
        viewModel.addTable(this, TableEntity(5, "Table 05", 0))
        viewModel.addTable(this, TableEntity(6, "Table 06", 0))
        viewModel.addTable(this, TableEntity(7, "Table 07", 0))
        viewModel.addTable(this, TableEntity(8, "Table 08", 0))
        viewModel.addTable(this, TableEntity(9, "Table 09", 0))

        /** 3. Category */
        viewModel.addCategory(CategoryEntity(1, "FOODS"))
        viewModel.addCategory(CategoryEntity(2, "DRINKS"))
        viewModel.addCategory(CategoryEntity(3, "DESSERTS"))

        /** 4. Item */
        viewModel.addCategoryItem(ItemEntity(0, "Mon Nhau 1", 131.1f, "", 5, 1))
        viewModel.addCategoryItem(ItemEntity(0, "Mon Nhau 2", 1111.1f, "", 1, 2))
        viewModel.addCategoryItem(ItemEntity(0, "Mon Nhau 3", 22.1f, "", 3, 3))
        viewModel.addCategoryItem(ItemEntity(0, "Mon Nhau 5", 13.1f, "", 5, 1))
        viewModel.addCategoryItem(ItemEntity(0, "Mon Nhau 6", 15.1f, "", 1, 2))
        viewModel.addCategoryItem(ItemEntity(0, "Mon Nhau 7", 111.1f, "", 3, 3))

        /** 5. Customer  */
        viewModel.addCustomer(CustomerEntity(1, "Quang 1", "08034931491", "19950302"))
        viewModel.addCustomer(CustomerEntity(2, "Quang 2", "08034931492", "19950303"))
        viewModel.addCustomer(CustomerEntity(3, "Quang 3", "08034931493", "19950304"))

        /** 6. Account_Shift  */

    }

    private fun startLoginActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, DELAY_TIME)
    }

    override fun getInflaterViewBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }
}