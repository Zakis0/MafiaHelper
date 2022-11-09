package com.example.mafiahelper.roles

import com.example.mafiahelper.*

class Detective: Role() {
    override val name = Roles.DETECTIVE
    override val team = Teams.TOWN
    override val img = R.drawable.ic_launcher_background

    override fun action(player: Player) {
        println(player.role.team)
    }
}