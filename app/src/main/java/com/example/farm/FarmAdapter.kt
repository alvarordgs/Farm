package com.example.farm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FarmAdapter(private val context : Context, private val farms: MutableList<Farm>): RecyclerView.Adapter<FarmAdapter.FarmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmViewHolder {
        val listItem =
            LayoutInflater.from(context).inflate(R.layout.activity_list_item, parent, false)

        return FarmViewHolder(listItem)
    }

    override fun getItemCount(): Int = farms.size

    override fun onBindViewHolder(holder: FarmViewHolder, position: Int) {
        val farm = farms[position]

        holder.register.text = farm.register
        holder.name.text = farm.name
        holder.value.text = farm.value.toString()
        holder.latitude.text = farm.latitude.toString()
        holder.longitude.text = farm.longitude.toString()
    }


    private fun onClickEditFarm(position: Int, context: Context) {
        val farm = farms[position]

        val intent = Intent(context, EditFarm::class.java)
        val bundle = Bundle()

        bundle.putString("register", farm.register)
        bundle.putString("name", farm.name)
        bundle.putFloat("value", farm.value)
        bundle.putFloat("latitude", farm.latitude)
        bundle.putFloat("longitude", farm.longitude)
        intent.putExtras(bundle)

        if (context is Activity) {
            context.startActivity(intent)
        }

        notifyItemChanged(position)
    }

    private fun onClickDeleteFarm(position: Int, context: Context) {
        val farm = farms[position]
        val farmDAO = FarmDAO(context)

        val isDeleted = farmDAO.delete(farm.register)

        if (isDeleted) {
            farms.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(context, "Farm deleted", Toast.LENGTH_SHORT).show()
        } else {
            notifyItemChanged(position)
            Toast.makeText(context, "Error deleting farm", Toast.LENGTH_SHORT).show()
        }
    }

    inner class FarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val register: TextView = itemView.findViewById(R.id.listItemRegisterValue)
        val name: TextView = itemView.findViewById(R.id.listItemNameValue)
        val value: TextView = itemView.findViewById(R.id.listItemValueValue)
        val latitude: TextView = itemView.findViewById(R.id.listItemLatitudeValue)
        val longitude: TextView = itemView.findViewById(R.id.listItemLongitudeValue)
        val editButton: Button = itemView.findViewById(R.id.listItemEditBtn)
        val deleteButton: Button = itemView.findViewById(R.id.listItemDeleteBtn)


        init {
            editButton.setOnClickListener {
                onClickEditFarm(adapterPosition, context)
            }

            deleteButton.setOnClickListener {
                onClickDeleteFarm(adapterPosition, context)
            }
        }

    }
}