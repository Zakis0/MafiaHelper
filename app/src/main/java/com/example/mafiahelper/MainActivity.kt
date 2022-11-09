package com.example.mafiahelper

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mafiahelper.adapters.PlayerAdapter
import com.example.mafiahelper.databinding.ActivityMainBinding
import com.example.mafiahelper.roles.Mafia
import java.text.ParsePosition

class MainActivity : AppCompatActivity(), PlayerAdapter.Listener {
    private lateinit var bindingClass: ActivityMainBinding
    private val adapter = PlayerAdapter(this)
    private var createPlayerLauncher: ActivityResultLauncher<Intent>? = null
    private var editPlayerLauncher: ActivityResultLauncher<Intent>? = null

    private var timer: CountDownTimer? = null

    private val playerList = mutableListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        initRecyclerView()
        
        createPlayerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val player = it.data?.getSerializableExtra(CREATE) as Player
                playerList.add(player)
                adapter.addPlayer(player)
            }
        }
        editPlayerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val player = it.data?.getSerializableExtra(EDIT_PLAYER) as Player
                val position = it.data?.getIntExtra(EDIT_POSITION, -1)
                if (position != null && position != -1) {
                    playerList[position] = player
                    adapter.editPlayer(position, player)
                }
            }
            else if (it.resultCode == RESULT_DELETE) {
                val position = it.data?.getSerializableExtra(DELETE) as Int
                playerList.removeAt(position)
                adapter.delPlayer(position)
            }
        }
    }

    fun initRecyclerView() {
        bindingClass.apply {
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity, NUM_OF_PLAYERS_IN_ROW)
            recyclerView.adapter = adapter
            addPlayerBtn.setOnClickListener {
                createPlayerLauncher?.launch(Intent(this@MainActivity, EditActivity::class.java))
            }
        }
    }

    override fun onClick(position: Int) {
        editPlayerLauncher?.launch(Intent(this@MainActivity, EditPlayer::class.java).apply {
            putExtra(EDIT_PLAYER, playerList[position])
            putExtra(EDIT_POSITION, position)
        })
    }

    fun startTimer() {
        val time: Long = 5000

        timer?.cancel()
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(time: Long) {
                //bindingClass.textView.text = timeNormalize(time)
            }
            override fun onFinish() {
                //bindingClass.textView.text = "finish"
            }
        }.start()
    }
    fun stopTimer() {
        timer?.cancel()
    }

    fun endNight() {
        playerList.forEach {
            it.implementEffects()
        }
        deathHookCheck()
    }
    fun deathHookCheck() {
        playerList.forEach { player ->
            if (player.statuses.contains(Statuses.DEAD) && player.role.name == Roles.HOOKER) {
                playerList.forEach {
                    if(it.actionEffects.contains(Actions.HOOK) && !it.effects.contains(Effects.HEALED)) {
                        it.clearStatuses()
                        it.statuses.add(Statuses.DEAD)
                    }
                }
            }
        }
    }
    fun death() {
        deathHookCheck()
        playerList.forEach { player ->
            if (player.statuses.contains(Statuses.DEAD)) {

            }
        }
    }
}
