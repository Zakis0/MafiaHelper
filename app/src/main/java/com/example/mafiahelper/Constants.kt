package com.example.mafiahelper

import com.example.mafiahelper.roles.*

const val CREATE = "createPlayer"
const val EDIT = "editPlayer"
const val DELETE = "deletePlayer"

const val EDIT_PLAYER = "editPlayerPlayer"
const val EDIT_POSITION = "editPlayerPosition"

const val LOG = "MyLog"

const val RESULT_DELETE = 23812364

const val NUM_OF_PLAYERS_IN_ROW = 3

val DEFAULT_ROLE = Citizen()

val AVAILABLE_ROLES = arrayListOf (
    Citizen(),
    Mafia(),
    Detective(),
    Doctor(),
    Maniac(),
//    Hooker(),
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