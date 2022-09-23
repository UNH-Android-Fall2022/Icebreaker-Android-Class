package com.example.icebreaker_android_f22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import androidx.appcompat.app.AppCompatDelegate
import com.example.icebreaker_android_f22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val TAG = "IcebreakerAndroidF22Tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetQuestion.setOnClickListener {
            // If the button gets pressed, this code will urn
            Log.d(TAG,"Button Get Question was pressed")
            getQuestionsFromFirebase()
        }

        binding.btnSubmit.setOnClickListener {

            Log.d(TAG,"Button Submit was pressed")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getQuestionsFromFirebase(){
        Log.d(TAG,"Fetching questions from database...")
    }
}