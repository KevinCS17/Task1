package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.task1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CoroutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = CoroutineRepository(getNetworkService())
        viewModel = ViewModelProvider(this, CoroutineViewModel.FACTORY(repository))
            .get(CoroutineViewModel::class.java)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.btnSearch.setOnClickListener {
            viewModel.onButtonSearch(binding.etData.text.toString())

            var int : Int = binding.etData.text.toString().toInt()
            if(int < 1 || int > 10){
                binding.txtName.text = "Data Not Available"
                binding.txtUsername.text = "Data Not Available"
                binding.txtEmail.text = "Data Not Available"
                binding.txtCity.text = "Data Not Available"
            }
        }
        viewModel.data.observe(this){value->
            value?.let {
                if(it.isNotEmpty()){
                    binding.txtName.text = it[0].name
                    binding.txtUsername.text = it[0].username
                    binding.txtEmail.text = it[0].email
                    binding.txtCity.text = it[0].address.city
                }
            }
        }
    }
}