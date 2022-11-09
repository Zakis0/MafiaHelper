package com.example.mafiahelper

import com.example.mafiahelper.roles.Role
import java.io.Serializable

class Player(val name: String, val role: Role): Serializable {
    val actionEffects: MutableList<Actions> = mutableListOf()
    val effects: MutableList<Effects> = mutableListOf()
    val statuses: MutableList<Statuses> = mutableListOf()

    var numOfVotes = 0

//    var haveVoted = false

    fun addActionEffect(eff: Actions) {
        actionEffects.add(eff)
    }
    fun clearEffects() {
        effects.clear()
    }
    fun clearActionEffect() {
        actionEffects.clear()
    }
    fun clearStatuses() {
        statuses.clear()
    }
    fun printEffects() {
        effects.forEach {
            println(it)
        }
    }
    fun printActionEffect() {
        actionEffects.forEach {
            println(it)
        }
    }
    fun printStatuses(): String {
        var result = ""
        statuses.forEach {
            result += it
            result += " "
        }
        return result
    }
//    fun vote(player: Player) {
//        if (!haveVoted) {
//            player.numOfVotes++
//            haveVoted = true
//        }
//    }
    fun resetVotes() {
        numOfVotes = 0
//        haveVoted = false
    }
}

fun Player.implementEffects() {
    if (actionEffects.contains(Actions.ATTACK) &&
        !actionEffects.contains(Actions.HEAL) && !actionEffects.contains(Actions.HOOK)) {
        statuses.add(Statuses.DEAD)
    }
    else {
        statuses.add(Statuses.ALIVE)
    }
    if (actionEffects.contains(Actions.HOOK)) {
        statuses.add(Statuses.SILENCE)
    }
}
