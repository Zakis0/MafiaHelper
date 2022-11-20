package com.example.mafiahelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mafiahelper.adapters.PlayerAdapter
import com.example.mafiahelper.adapters.TimerAdapter
import com.example.mafiahelper.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), PlayerAdapter.Listener, TimerAdapter.Listener {
    private lateinit var bindingClass: ActivityMainBinding

    private val adapterPlayer = PlayerAdapter(this)
    private var createPlayerLauncher: ActivityResultLauncher<Intent>? = null
    private var editPlayerLauncher: ActivityResultLauncher<Intent>? = null

    private val adapterTimer = TimerAdapter(this)
    private var createTimerLauncher: ActivityResultLauncher<Intent>? = null
    private var editTimerLauncher: ActivityResultLauncher<Intent>? = null

    private var timer: CountDownTimer? = null
    private lateinit var timerPauseTime: Timer
    private var timerPaused = false
    private var timerStarted = false

    private val playerList = mutableListOf<Player>()
    private val timerList = mutableListOf<Timer>()

    private lateinit var activeMode: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        initRecyclerView()

        activeMode = bindingClass.playersMode

        for (timer in BASE_TIMERS) {
            timerList.add(timer)
            adapterTimer.addTimer(timer)
        }
        
        createPlayerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val player = it.data?.getSerializableExtra(CREATE) as Player
                playerList.add(player)
                adapterPlayer.addPlayer(player)
            }
        }
        editPlayerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val player = it.data?.getSerializableExtra(EDIT_PLAYER) as Player
                val position = it.data?.getIntExtra(EDIT_POSITION, -1)
                if (position != null && position != -1) {
                    playerList[position] = player
                    adapterPlayer.editPlayer(position, player)
                }
            }
            else if (it.resultCode == RESULT_DELETE) {
                val position = it.data?.getSerializableExtra(DELETE) as Int
                playerList.removeAt(position)
                adapterPlayer.delPlayer(position)
            }
        }
        bindingClass.apply {
            createTimerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val timer = it.data?.getSerializableExtra(CREATE) as Timer
                    timerList.add(timer)
                    adapterTimer.addTimer(timer)
                }
                changeActiveMode(timerMode)
            }
            editTimerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val timer = it.data?.getSerializableExtra(EDIT_TIMER) as Timer
                    val position = it.data?.getIntExtra(EDIT_POSITION, -1)
                    if (position != null && position != -1) {
                        timerList[position] = timer
                        adapterTimer.editTimer(position, timer)
                    }
                }
                else if (it.resultCode == RESULT_DELETE) {
                    val position = it.data?.getSerializableExtra(DELETE) as Int
                    timerList.removeAt(position)
                    adapterTimer.delTimer(position)
                }
                changeActiveMode(timerMode)
            }
            timerStartBtn.setOnClickListener {
                timer()
            }
            timerStopBtn.setOnClickListener {
                stopTimer()
            }
            randomGenerateBtn.setOnClickListener {
                if (randomStartRange.text.toString().isNotEmpty() &&
                    randomEndRange.text.toString().isNotEmpty()) {
                    val startRange = Integer.parseInt(randomStartRange.text.toString())
                    val endRange = Integer.parseInt(randomEndRange.text.toString())

                    if (startRange <= endRange) {
                        randomResult.text = getRandInt(startRange, endRange).toString()
                    }
                }
            }
            randomClearBtn.setOnClickListener {
                randomStartRange.text = null
                randomEndRange.text = null
                randomResult.text = null
            }
            bNav.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.players -> {
                        changeActiveMode(playersMode)
                    }
                    R.id.night -> {
                        changeActiveMode(nightMode)
                    }
                    R.id.timer -> {
                        changeActiveMode(timerMode)
                        if (timerStarted) {
                            bindingClass.timerStopBtn.visibility = View.VISIBLE
                        }
                        else {
                            bindingClass.timerStopBtn.visibility = View.GONE
                        }
                    }
                    R.id.random -> {
                        changeActiveMode(randomMode)
                    }
                }
                true
            }
        }
    }
    fun initRecyclerView() {
        bindingClass.apply {
            playersRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, NUM_OF_PLAYERS_IN_ROW)
            playersRecyclerView.adapter = adapterPlayer
            addPlayerBtn.setOnClickListener {
                createPlayerLauncher?.launch(Intent(this@MainActivity, AddPlayerActivity::class.java))
            }

            timerRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, NUM_OF_TIMERS_IN_ROW)
            timerRecyclerView.adapter = adapterTimer
            timerAddTimerBtn.setOnClickListener {
                createTimerLauncher?.launch(Intent(this@MainActivity, AddTimerActivity::class.java))
            }
        }
    }
    override fun onClickPlayer(position: Int) {
        editPlayerLauncher?.launch(Intent(this@MainActivity, EditPlayerActivity::class.java).apply {
            putExtra(EDIT_PLAYER, playerList[position])
            putExtra(EDIT_POSITION, position)
        })
    }
    override fun onLongClickTimer(position: Int) {
        editTimerLauncher?.launch(Intent(this@MainActivity, EditTimerActivity::class.java).apply {
            putExtra(EDIT_TIMER, timerList[position])
            putExtra(EDIT_POSITION, position)
        })
    }
    override fun onClickTimer(timer: Timer) {
        if (!timerStarted) {
            bindingClass.timerTimer.text = timeNormalize(timer)
        }
        else {
            Toast.makeText(this@MainActivity, STOP_TIMER_HINT, Toast.LENGTH_SHORT).show()
        }
    }
    fun startTimer(timer_: Timer) {
        val time = (timer_.minutes.toLong() * 60 + timer_.seconds) * TIMER_INTERVAL
        timer?.cancel()
        timer = object : CountDownTimer(time, 1) {
            override fun onTick(time: Long) {
                bindingClass.timerTimer.text = timeNormalize(time)
            }
            override fun onFinish() {
                stopTimer()
            }
        }.start()
    }
    fun timer() {
        if (!timerStarted) {
            val time = getTime(bindingClass.timerTimer.text.toString())
            if (!time.isEqual(Timer.baseTimer)) {
                timerStarted = true
                bindingClass.timerStartBtn.text = PAUSE_TIMER
                startTimer(time)
                bindingClass.timerStopBtn.visibility = View.VISIBLE
            }
        }
        else {
            if (!timerPaused) {
                bindingClass.timerStartBtn.text = UNPAUSE_TIMER
                timerPauseTime = getTime(bindingClass.timerTimer.text.toString())
                timerPaused = true
                timer?.cancel()
            }
            else {
                bindingClass.timerStartBtn.text = PAUSE_TIMER
                startTimer(Timer(timerPauseTime.minutes, timerPauseTime.seconds + 1))
                timerPaused = false
            }
        }
    }
    fun stopTimer() {
        timer?.cancel()
        bindingClass.timerStartBtn.text = START_TIMER
        timerStarted = false
        timerPaused = false
        timerPauseTime = Timer(0, 0)
        bindingClass.timerTimer.text = BASE_TIMER_TIME
        bindingClass.timerStopBtn.visibility = View.GONE
    }
    fun changeActiveMode(newMode: ConstraintLayout) {
        if (newMode != activeMode) {
            newMode.visibility = View.VISIBLE
            activeMode.visibility = View.GONE
            activeMode = newMode
        }
    }
}
