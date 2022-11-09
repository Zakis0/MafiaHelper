package com.example.mafiahelper.roles

import com.example.mafiahelper.*

class Mafia: Role() {
    override val name = Roles.MAFIA
    override val team = Teams.MAFIA
    override val img = R.drawable.ic_launcher_foreground

    override fun action(player: Player) {
        if (!player.actionEffects.contains(Actions.ATTACK)) {
            player.addActionEffect(Actions.ATTACK)
        }
    }
}