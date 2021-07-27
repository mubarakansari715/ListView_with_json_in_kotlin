package com.example.listview_with_json_in_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyAdapter (private val context: Context, private val list : ArrayList<ModelClass>):BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = LayoutInflater.from(context).inflate(R.layout.custom_listview,parent,false)
        val name = view.findViewById<TextView>(R.id.tv_name)
        val email = view.findViewById<TextView>(R.id.tv_email)
        val body = view.findViewById<TextView>(R.id.tv_body)
        name.text = list[position].name
        email.text = list[position].email
        body.text = list [position].body
        return view

    }
}