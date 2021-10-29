package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.task1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = CoroutineRepository(getNetworkService())
        val viewModel = ViewModelProvider(this, CoroutineViewModel.FACTORY(repository))
            .get(CoroutineViewModel::class.java)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.btnSearch.setOnClickListener {
            var input : String = binding.etData.text.toString()
            viewModel.onButtonSearch(binding.etData.text.toString())
        }
    }
}