package com.example.yelpclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FoodLocation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_location)

        val button = findViewById<Button>(R.id.btSearch)
        button.setOnClickListener {
            callActivity()
        }
    }

    private fun callActivity() {
        val editText = findViewById<EditText>(R.id.etFood)
        val food = editText.text.toString()
        val editText2 = findViewById<EditText>(R.id.etLocation)
        val location = editText2.text.toString()

        val intent = Intent(this,MainActivity::class.java).also {
            it.putExtra("FoodName",food)
            it.putExtra("Location",location)
            startActivity(it)
        }
    }
}