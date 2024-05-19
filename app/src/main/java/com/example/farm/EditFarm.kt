package com.example.farm

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditFarm : AppCompatActivity() {
    lateinit var register: String
    lateinit var name: EditText
    lateinit var value: EditText
    lateinit var latitude: EditText
    lateinit var longitude: EditText
    lateinit var btnEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_farm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = findViewById(R.id.editNameEditText)
        value = findViewById(R.id.editValueEditText)
        latitude = findViewById(R.id.editLatitudeEditText)
        longitude = findViewById(R.id.editLongitudeEditText)
        btnEdit = findViewById(R.id.editFarmButton)

        intent.extras?.let {
            Log.i("EditFarm", it.toString())
            register = it.getString("register").toString()
            name.setText(it.getString("name"))
            value.setText(it.getFloat("value").toString())
            latitude.setText(it.getFloat("latitude").toString())
            longitude.setText(it.getFloat("longitude").toString())
        }

        btnEdit.setOnClickListener {
            updateFarm()
        }

    }

    private fun updateFarm() {
        try {
            val name = name.text.toString()
            val value = value.text.toString().toFloatOrNull() ?: 0.0f
            val latitude = latitude.text.toString().toFloatOrNull() ?: 0.0f
            val longitude = longitude.text.toString().toFloatOrNull() ?: 0.0f

            val farm = Farm(register, name, value, latitude, longitude)
            val farmDAO = FarmDAO(this)

            val isUpdated = farmDAO.edit(farm)

            if (isUpdated) {
                Toast.makeText(this, "Farm data updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }

        }catch (e: Exception){
            Toast.makeText(this, "It was not possible to update the farm data", Toast.LENGTH_SHORT).show()
        }

    }
}