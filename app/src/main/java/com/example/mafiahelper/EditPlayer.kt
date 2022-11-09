package com.example.mafiahelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.example.mafiahelper.adapters.RoleAdapters
import com.example.mafiahelper.databinding.ActivityEditBinding
import com.example.mafiahelper.databinding.ActivityEditPlayerBinding
import com.example.mafiahelper.model.RoleItem
import com.example.mafiahelper.roles.*

class EditPlayer : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var bindingClass: ActivityEditPlayerBinding

    private var roleList: ArrayList<RoleItem>? = null
    private var roleAdapters: RoleAdapters? = null

    private lateinit var playerRole: Role
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityEditPlayerBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        initButtons()

        val player = intent.getSerializableExtra(EDIT_PLAYER) as Player
        position = intent.getSerializableExtra(EDIT_POSITION) as Int

        roleList = ArrayList()
        roleList = setDataList()
        roleAdapters = RoleAdapters(applicationContext, roleList!!)
        bindingClass.playerRolesGridView.adapter = roleAdapters
        bindingClass.playerRolesGridView.onItemClickListener = this

        bindingClass.playerName.setText(player.name)
        bindingClass.roleImg.setImageResource(player.role.img)
        bindingClass.roleName.text = player.role.name.string
        playerRole = player.role

        bindingClass.roleCardView.setOnClickListener {
            bindingClass.playerRolesGridView.visibility = View.VISIBLE
            bindingClass.roleCardView.visibility = View.GONE
            changeBtnVisibility()
        }
        bindingClass.playerRolesGridView.visibility = View.GONE
    }
    private fun initButtons() = with(bindingClass) {
        doneBtn.setOnClickListener {
            val player = Player(playerName.text.toString(), playerRole)
            val editIntent = Intent().apply {
                putExtra(EDIT_PLAYER, player)
                putExtra(EDIT_POSITION, position)
            }
            setResult(RESULT_OK, editIntent)
            finish()
        }
        delPlayerBtn.setOnClickListener {
            val editIntent = Intent().apply {
                putExtra(DELETE, position)
            }
            setResult(RESULT_DELETE, editIntent)
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