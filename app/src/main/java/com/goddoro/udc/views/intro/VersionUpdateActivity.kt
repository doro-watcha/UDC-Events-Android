package com.goddoro.udc.views.intro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.udc.databinding.ActivityVersionUpdateBinding

class VersionUpdateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVersionUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVersionUpdateBinding.inflate(LayoutInflater.from(this))

        binding.lifecycleOwner = this

        setContentView(binding.root)

        initView()
    }

    private fun initView() {


        binding.apply {

            txtVersionUpdate.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.goddoro.udc")
                    setPackage("com.android.vending")
                }
                startActivity(intent)

            }
        }

    }
}