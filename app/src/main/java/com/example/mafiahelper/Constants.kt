package com.example.mafiahelper

import com.example.mafiahelper.roles.*

const val CREATE = "createItem"
const val DELETE = "deleteItem"

const val EDIT_PLAYER = "editPlayerPlayer"
const val EDIT_TIMER = "editTimerTimer"

const val EDIT_POSITION = "editPosition"

const val START_TIMER = "Start timer"
const val PAUSE_TIMER = "Pause timer"
const val UNPAUSE_TIMER = "Unpause timer"
const val STOP_TIMER_HINT = "Stop timer"

const val BASE_TIMER_TIME = "00:00"

const val TIMER_INTERVAL = 1000

const val LOG = "MyLog"

const val RESULT_DELETE = 271828

const val NUM_OF_PLAYERS_IN_ROW = 3
const val NUM_OF_TIMERS_IN_ROW = 2

val DEFAULT_ROLE = Citizen()

val BASE_TIMERS = arrayListOf (
    Timer(0, 30),
    Timer(1, 0),
    Timer(2, 0),
    Timer(3, 0),
)

val AVAILABLE_ROLES = arrayListOf (
    Citizen(),
    Mafia(),
    Detective(),
    Doctor(),
    Maniac(),
    Hooker(),
    )

enum class Roles(val string: String) {
    CITIZEN("Citizen"),
    MAFIA("Mafia"),
    DETECTIVE("Detective"),
    DOCTOR("Doctor"),
    MANIAC("Maniac"),
    HOOKER("Hooker"),
}

enum class Teams {
    MAFIA,
    TOWN,
}

enum class Actions {
    ATTACK,
    HEAL,
    HOOK,
}

enum class Effects {
    HEALED,
    HOOKED,
}

enum class Statuses {
    ALIVE,
    DEAD,
    SILENCE,
}