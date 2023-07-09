package com.example.restaurantpos.ui.manager.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentManagerHomeBinding
import com.quang.demo1.network.RetrofitClient
import com.quang.demo1.network.ServiceDataModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagerHomeFragment : Fragment() {

    lateinit var binding: FragmentManagerHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Shift */
        binding.btnShift.setOnClickListener {
            findNavController().navigate(R.id.action_mainManagerFragment_to_shiftFragment)
        }

        /** Statistic */
        binding.txtStatistic.setOnClickListener {
            findNavController().navigate(R.id.action_mainManagerFragment_to_statisticFragment)
        }

        /** Call API */
        val call = RetrofitClient.apiInterface.getWeather(
            10.99,
            44.34,
            "4d7a2eec93e88f5e5c38da21836baa09"
        )

        call.enqueue(object : Callback<ServiceDataModel> {
            override fun onResponse(
                call: Call<ServiceDataModel>,
                response: Response<ServiceDataModel>
            ) {
                val boby = response.body()
                binding.txtTemp.text = "${boby?.main?.temp?.minus(273.7)}°C"
                Picasso.get().load(
                    "http://openweathermap.org/img/w/${boby?.weather?.get(0)?.icon}.png"
                ).into(binding.imgWeather)
                Log.d("truongpa", "http://openweathermap.org/img/w/${boby?.weather?.get(0)?.icon}.png")
            }

            override fun onFailure(call: Call<ServiceDataModel>, t: Throwable) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        } )

    }
}