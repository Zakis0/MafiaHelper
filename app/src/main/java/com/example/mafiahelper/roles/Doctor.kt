package com.example.mafiahelper.roles

import com.example.mafiahelper.*

class Doctor: Role() {
    override val name = Roles.DOCTOR
    override val team = Teams.TOWN
    override val img = R.drawable.ic_launcher_background

    override fun action(player: Player) {
        if (!player.actionEffects.contains(Actions.HEAL) && !player.effects.contains(Effects.HEALED)) {
            player.addActionEffect(Actions.HEAL)
        }
    }
}