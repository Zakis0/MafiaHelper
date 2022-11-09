package com.example.mafiahelper.roles

import com.example.mafiahelper.*

class Hooker: Role() {
    override val name = Roles.HOOKER
    override val team = Teams.TOWN
    override val img = R.drawable.ic_launcher_background

    override fun action(player: Player) {
        if (!player.actionEffects.contains(Actions.HOOK) && !player.effects.contains(Effects.HOOKED)) {
            player.addActionEffect(Actions.HOOK)
        }
    }
}