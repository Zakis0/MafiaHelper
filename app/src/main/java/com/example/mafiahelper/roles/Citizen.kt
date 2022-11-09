package com.example.mafiahelper.roles

import com.example.mafiahelper.*

class Citizen: Role() {
    override val name = Roles.CITIZEN
    override val team = Teams.TOWN
    override val img = R.drawable.ic_launcher_background
}