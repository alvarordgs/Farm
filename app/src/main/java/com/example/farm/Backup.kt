package com.example.farm

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class Backup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_backup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val filePath = intent.getStringExtra("filePath")

        if(filePath != null) {
            openFile(filePath)
        } else {
            Toast.makeText(applicationContext, "No file path found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openFile(filePath: String) {
        val file = File(filePath)

        try {
            val inputStream = FileInputStream(file)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val stringBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while(line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
            inputStreamReader.close()
            inputStream.close()

            val textViewContentBackup = findViewById<TextView>(R.id.backupText)
            textViewContentBackup.text = stringBuilder.toString()
        } catch(e: Exception) {
            Toast.makeText(applicationContext, "Error reading file", Toast.LENGTH_SHORT).show()
        }
    }
}