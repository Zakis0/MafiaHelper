package com.example.mafiahelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.mafiahelper.adapters.RoleAdapters
import com.example.mafiahelper.adapters.TimerAdapter
import com.example.mafiahelper.databinding.ActivityAddPlayerBinding
import com.example.mafiahelper.databinding.ActivityAddTimerBinding
import com.example.mafiahelper.model.RoleItem
import com.example.mafiahelper.model.TimerItem
import com.example.mafiahelper.roles.Role

class AddTimerActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityAddTimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityAddTimerBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        initButtons()
    }

    private fun initButtons() = with(bindingClass) {
        addTimerBtn.setOnClickListener {
            if (bindingClass.timer.text.toString().isNotEmpty()) {
                val timer = getTime(bindingClass.timer.text.toString())
                if (timer != Timer.nullTimer && isNormalTime(timer)) {
                    val editIntent = Intent().putExtra(CREATE, timer)
                    setResult(RESULT_OK, editIntent)
                    finish()
                }
                else {
                    Toast.makeText(this@AddTimerActivity, "Incorrect time", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}