package com.example.unitconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = Converter()
        val secondFragment = AddUnits()

        val btnFragment1 = findViewById<Button>(R.id.btnConverter)
        val btnFragment2 = findViewById<Button>(R.id.btnUnitAdder)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            addToBackStack(null)
            commit()
        }

        btnFragment1.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, firstFragment)
                addToBackStack(null)
                commit()
            }
        }

        btnFragment2.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, secondFragment)
                addToBackStack(null)
                commit()
            }
        }

    }
}




