package com.example.mafiahelper

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mafiahelper.Timer
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.mafiahelper.adapters.RoleAdapters
import com.example.mafiahelper.databinding.ActivityEditPlayerBinding
import com.example.mafiahelper.databinding.ActivityEditTimerBinding
import com.example.mafiahelper.model.RoleItem
import com.example.mafiahelper.roles.Role

class EditTimerActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityEditTimerBinding
    private var position: Int = 0
    private lateinit var receivedTimer: Timer

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityEditTimerBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        initButtons()

        receivedTimer = intent.getSerializableExtra(EDIT_TIMER) as Timer
        position = intent.getSerializableExtra(EDIT_POSITION) as Int

        bindingClass.timer.setText(timeNormalize(Timer(receivedTimer.minutes, receivedTimer.seconds)))
    }
    private fun initButtons() {
        bindingClass.doneBtn.setOnClickListener {
            val timer = getTime(bindingClass.timer.text.toString())
            timer.dbID = receivedTimer.dbID
            if (timer != Timer.nullTimer && isNormalTime(timer)) {
                val editIntent = Intent().apply {
                    putExtra(EDIT_TIMER, timer)
                    putExtra(EDIT_POSITION, position)
                }
                setResult(RESULT_OK, editIntent)
                finish()
            }
            else {
                Toast.makeText(this@EditTimerActivity, "Incorrect time", Toast.LENGTH_SHORT).show()
            }
        }
        bindingClass.delTimerBtn.setOnClickListener {
            val editIntent = Intent().putExtra(DELETE, position)
            setResult(RESULT_DELETE, editIntent)
            finish()
        }
    }
}