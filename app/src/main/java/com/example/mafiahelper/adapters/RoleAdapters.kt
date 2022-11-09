package com.example.mafiahelper.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mafiahelper.R
import com.example.mafiahelper.databinding.PlayerItemBinding
import com.example.mafiahelper.model.RoleItem

class RoleAdapters(var context: Context,  var arrayList: ArrayList<RoleItem>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.card_view_item_grid, null)
        var img:ImageView = view.findViewById(R.id.roleImg)
        var name: TextView = view.findViewById(R.id.roleName)

        var listItem:RoleItem = arrayList.get(position)
        img.setImageResource(listItem.icon!!)
        name.text = listItem.role.string

        return view
    }
}