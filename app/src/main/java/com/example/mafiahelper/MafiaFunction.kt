import com.example.mafiahelper.*

fun endNight(playerList: MutableList<Player>) {
    playerList.forEach {
        it.implementEffects()
    }
    deathHookCheck(playerList)
}
fun deathHookCheck(playerList: MutableList<Player>) {
    playerList.forEach { player ->
        if (player.statuses.contains(Statuses.DEAD) && player.role.name == Roles.HOOKER) {
            playerList.forEach {
                if(it.actionEffects.contains(Actions.HOOK) && !it.effects.contains(Effects.HEALED)) {
                    it.clearStatuses()
                    it.statuses.add(Statuses.DEAD)
                }
            }
        }
    }
}
fun death(playerList: MutableList<Player>) {
    deathHookCheck(playerList)
    playerList.forEach { player ->
        if (player.statuses.contains(Statuses.DEAD)) {

        }
    }
}
