package com.example.mafiahelper.roles

import com.example.mafiahelper.*

class Maniac: Role() {
    override val name = Roles.MANIAC
    override val team = Teams.TOWN
    override val img = R.drawable.ic_launcher_background

    override fun action(player: Player) {
        if (!player.actionEffects.contains(Actions.ATTACK)) {
            player.addActionEffect(Actions.ATTACK)
        }
    }
}