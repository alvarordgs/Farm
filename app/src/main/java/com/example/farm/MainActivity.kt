package com.example.farm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private val filePath = "MeuArquivo"
    private var externalFile: File? = null

    lateinit var registerBtn: Button
    lateinit var listFarmBtn: Button
    lateinit var backupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerBtn = findViewById(R.id.registerFarmMainButton)
        listFarmBtn = findViewById(R.id.listFarmButton)
        backupButton = findViewById(R.id.backupButton)


        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterFarm::class.java)
            startActivity(intent)
        }

        listFarmBtn.setOnClickListener {
            val intent = Intent(this, ListFarm::class.java)
            startActivity(intent)
        }

        backupButton.setOnClickListener {
            backup()
        }

    }

    private fun backup() {
        val backupDAO = BackupDAO(this)
        val data = backupDAO.create()

        val fileName = "backup.txt"
        externalFile = File(getExternalFilesDir(filePath), fileName)

        try {
            val fileOutputStream = FileOutputStream(externalFile)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()

            val intent = Intent(this, Backup::class.java)
            intent.putExtra("filePath", externalFile!!.absolutePath)
            startActivity(intent)

            Toast.makeText(applicationContext, "Backup created", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error creating backup", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}