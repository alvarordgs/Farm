package com.example.farm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFarm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view_farm)

        val recyclerViewFarm = findViewById<RecyclerView>(R.id.recyclerViewListFarm)

        recyclerViewFarm.layoutManager = LinearLayoutManager(this)
        recyclerViewFarm.setHasFixedSize(true)

        val farmDAO = FarmDAO(this)

        val farmAdapter = FarmAdapter(this, farmDAO.list())
        recyclerViewFarm.adapter = farmAdapter
    }
}