package com.example.mafiahelper

import com.example.mafiahelper.roles.*

fun timeNormalize(time: Long): String {
    var minutes: Long = 0
    var seconds: Long = 0

    if (time >= 60000) {
        minutes = time / 60000
    }
    seconds = (time % 60000) / 1000
    if (minutes > 60) {
        return "00:00"
    }
    return if (minutes >= 10) minutes.toString() else "0${minutes}" + ":" +
            if (seconds >= 10) seconds.toString() else "0${seconds}"
}

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