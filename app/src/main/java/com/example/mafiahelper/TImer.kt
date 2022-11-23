package com.example.mafiahelper

import android.util.Log
import java.io.Serializable

class Timer(val minutes: Int, val seconds: Int, var dbID: Int? = null): Serializable {
    companion object {
        val nullTimer = Timer(-1, -1)
        val baseTimer = Timer(0, 0)
    }
    fun isEqual(otherTimer: Timer): Boolean = (seconds == otherTimer.seconds) && (minutes == otherTimer.minutes)
    fun printTimer(): String {
        return timeNormalize(Timer(minutes, seconds))
    }
    fun log() {
        Log.e(LOG, timeNormalize(Timer(minutes, seconds)))
    }
}