package com.example.mafiahelper.roles

import android.media.Image
import com.example.mafiahelper.*

abstract class Role: java.io.Serializable {
    abstract val name: Roles
    abstract val team: Teams
    abstract val img: Int

    open fun action(player: Player) {}
}