package com.example.icebreaker_android_f22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import androidx.appcompat.app.AppCompatDelegate
import com.example.icebreaker_android_f22.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val TAG = "IcebreakerAndroidF22Tag"
    private val db = Firebase.firestore
    private var questionBank: MutableList<Question>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        getQuestionsFromFirebase()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGetQuestion.setOnClickListener {
            // If the button gets pressed, this code will run
            Log.d(TAG,"Button Get Question was pressed")
            Log.d(TAG,"Question Grabbed: ${questionBank?.random()}")

            binding.txtQuestion.text = questionBank?.random().toString()
        }

        binding.btnSubmit.setOnClickListener {
            Log.d(TAG,"Button Submit was pressed")
            writeStudentToFirebase()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getQuestionsFromFirebase(){
        Log.d(TAG,"Fetching questions from database...")

        db.collection("questions")
            .get()
            .addOnSuccessListener { documents ->
                questionBank = mutableListOf()
                for(document in documents){
                    Log.d(TAG, "${document.id} => ${document.data}")

                    val question = document.toObject(Question::class.java)
                    Log.d(TAG, question.text)
                    questionBank!!.add(question)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents", exception)
            }
    }

    private fun writeStudentToFirebase(){

        val firstName = binding.txtFirstName
        val lastName = binding.txtLastName
        val prefName = binding.txtPreferredName
        val answer = binding.txtAnswer

        val student = hashMapOf(
            "firstName" to firstName.text.toString(),
            "lastName" to lastName.text.toString(),
            "prefName" to prefName.text.toString(),
            "answer" to answer.text.toString(),
            "question" to binding.txtQuestion.text
        )

        db.collection("students")
            .add(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,"Document successfully written with ID ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error adding document", exception)
            }
        
        firstName.setText("")
        lastName.setText("")
        prefName.setText("")
        answer.setText("")
    }
}