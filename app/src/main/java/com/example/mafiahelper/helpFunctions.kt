package com.example.mafiahelper

import android.util.Log
import com.example.mafiahelper.roles.*
import kotlin.random.Random

fun getRole(role: Roles): Role {
    return when(role) {
        Roles.MAFIA -> Mafia()
        Roles.DETECTIVE -> Detective()
        Roles.DOCTOR -> Doctor()
        Roles.HOOKER -> Hooker()
        Roles.MANIAC -> Maniac()
        else -> Citizen()
    }
}
fun getAllRoles(playerList: MutableList<Player>): MutableSet<Role> {
    val roles = mutableSetOf<Role>()
    playerList.forEach {
        roles.add(it.role)
    }
    return roles
}
fun getAllAliveRoles(playerList: MutableList<Player>): MutableSet<Role> {
    val roles = mutableSetOf<Role>()
    playerList.forEach {
        if (it.alive) {
            roles.add(it.role)
        }
    }
    return roles
}
fun timeNormalize(time: Long): String {
    var minutes: Long = 0
    var seconds: Long

    if (time >= 60000) {
        minutes = time / 60000
    }
    seconds = (time % 60000) / 1000
    if (minutes > 60) {
        return "00:00"
    }
    val min = if (minutes >= 10) minutes.toString() else "0${minutes}"
    val sec = if (seconds >= 10) seconds.toString() else "0${seconds}"

    return "$min:$sec"
}
fun timeNormalize(timer: Timer): String {
    val min = if (timer.minutes >= 10) timer.minutes.toString() else "0${timer.minutes}"
    val sec = if (timer.seconds >= 10) timer.seconds.toString() else "0${timer.seconds}"
    return "$min:$sec"
}
fun getRandInt(start: Int, end: Int): Int {
    return Random.nextInt(start, end + 1)
}
fun getTime(time: String): Timer {
    if (!time.contains(":")) {
        return Timer.nullTimer
    }
    val str = time.split(":".toRegex()).toTypedArray()
    return Timer(str[0].toInt(), str[1].toInt())
}
fun isNormalTime(timer: Timer): Boolean {
    if (timer.minutes in 0..59 && timer.seconds in 0..59) {
        return true
    }
    return false
}

