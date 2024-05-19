package com.example.farm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterFarm : AppCompatActivity() {

    lateinit var etRegister: EditText
    lateinit var etName: EditText
    lateinit var etFarmValue: EditText
    lateinit var etLatitude: EditText
    lateinit var etLongitude: EditText
    lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_farm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etRegister = findViewById(R.id.registerRegisterEditText)
        etName = findViewById(R.id.registerNameEditText)
        etFarmValue = findViewById(R.id.registerValueEditText)
        etLatitude = findViewById(R.id.registerLatitudeEditText)
        etLongitude = findViewById(R.id.registerLongitudeEditText)
        registerBtn = findViewById(R.id.registerFarmButton)

        registerBtn.setOnClickListener{
            this.register();
        }
    }

    private fun register() {
        try {
            val register = etRegister.text.toString()
            val name = etName.text.toString()
            val farmValue = etFarmValue.text.toString().toFloat()
            val latitude = etLatitude.text.toString().toFloat()
            val longitude = etLongitude.text.toString().toFloat()

            val farm = Farm(register, name, farmValue, latitude, longitude)
            val farmDAO = FarmDAO(this)

            val isCreated = farmDAO.insert(farm)

            if (isCreated) {
                clearFields()
                Toast.makeText(this, "Farm registered successfully!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message?:"Error registering farm!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        etRegister.text.clear()
        etName.text.clear()
        etFarmValue.text.clear()
        etLatitude.text.clear()
        etLongitude.text.clear()
    }

}