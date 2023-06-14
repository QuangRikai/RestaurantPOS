package com.example.restaurantpos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.restaurantpos.R
import com.example.restaurantpos.base.BaseActivity
import com.example.restaurantpos.databinding.ActivityMainReceptionistBinding

class MainReceptionistActivity : BaseActivity<ActivityMainReceptionistBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding.root


    }

    override fun initOnCreate() {

    }


    override fun getInflaterViewBinding(layoutInflater: LayoutInflater): ActivityMainReceptionistBinding {
        return ActivityMainReceptionistBinding.inflate(layoutInflater)
    }
}