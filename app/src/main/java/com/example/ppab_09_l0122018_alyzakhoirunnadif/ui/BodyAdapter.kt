package com.example.ppab_09_l0122018_alyzakhoirunnadif

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.Chara

class BodyAdapter(private val listCharacters: ArrayList<Chara>) : RecyclerView.Adapter<BodyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_body, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val character = listCharacters[position]
        viewHolder.tvName.text = character.name
        viewHolder.tvDescription.text = character.description
        Glide.with(viewHolder.itemView.context)
            .load(character.splashart)
            .into(viewHolder.ivSplashArt)
    }

    override fun getItemCount(): Int {
        return listCharacters.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivSplashArt: ImageView = view.findViewById(R.id.ivSplashArt)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
    }
}

