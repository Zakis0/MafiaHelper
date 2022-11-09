package com.example.mafiahelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Toast
import com.example.mafiahelper.adapters.RoleAdapters
import com.example.mafiahelper.databinding.ActivityEditBinding
import com.example.mafiahelper.model.RoleItem
import com.example.mafiahelper.roles.*

class EditActivity: AppCompatActivity(), OnItemClickListener {
    private lateinit var bindingClass: ActivityEditBinding

    private var roleList: ArrayList<RoleItem>? = null
    private var roleAdapters: RoleAdapters? = null

    private var playerRole: Role = DEFAULT_ROLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityEditBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        initButtons()

        roleList = ArrayList()
        roleList = setDataList()
        roleAdapters = RoleAdapters(applicationContext, roleList!!)
        bindingClass.playerRolesGridView.adapter = roleAdapters
        bindingClass.playerRolesGridView.onItemClickListener = this

        bindingClass.roleImg.setImageResource(DEFAULT_ROLE.img)
        bindingClass.roleName.text = DEFAULT_ROLE.name.string

        bindingClass.roleCardView.setOnClickListener {
            bindingClass.playerRolesGridView.visibility = View.VISIBLE
            bindingClass.roleCardView.visibility = View.GONE
            changeBtnVisibility()
        }
        bindingClass.playerRolesGridView.visibility = View.GONE
    }

    private fun initButtons() = with(bindingClass) {
        addPlayerBtn.setOnClickListener {
            val player = Player(playerName.text.toString(), playerRole)
            val editIntent = Intent().apply {
                putExtra(CREATE, player)
            }
            setResult(RESULT_OK, editIntent)
            finish()
        }
    }

    private fun setDataList(): ArrayList<RoleItem> {
        val roleList = ArrayList<RoleItem>()
        AVAILABLE_ROLES.forEach {
            roleList.add(RoleItem(it.img, it.name))
        }
        return roleList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val items: RoleItem = roleList!!.get(position)

        playerRole = getRole(items.role)

        bindingClass.roleImg.setImageResource(playerRole.img)
        bindingClass.roleName.text = playerRole.name.string

        bindingClass.playerRolesGridView.visibility = View.GONE
        bindingClass.roleCardView.visibility = View.VISIBLE
        changeBtnVisibility()
    }

    fun changeBtnVisibility() {
        if (bindingClass.btnsCardView.visibility == View.VISIBLE) {
            bindingClass.btnsCardView.visibility = View.GONE
        }
        else {
            bindingClass.btnsCardView.visibility = View.VISIBLE
        }
    }
}