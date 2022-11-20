package com.example.mafiahelper.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mafiahelper.LOG
import com.example.mafiahelper.Player
import com.example.mafiahelper.R
import com.example.mafiahelper.databinding.PlayerItemBinding

class PlayerAdapter(val listener: Listener): RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {
    val playerList = ArrayList<Player>()
    class PlayerHolder(item: View): RecyclerView.ViewHolder(item) {
        val bindingClass = PlayerItemBinding.bind(item)

        fun bind(player: Player, listener: Listener, position: Int) = with(bindingClass) {
            roleImg.setImageResource(player.role.img)
            roleName.text = player.role.name.string
            playerName.text = player.name
            itemView.setOnClickListener {
                listener.onClickPlayer(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return PlayerHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(playerList[position], listener, position)
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPlayer(player: Player) {
        playerList.add(player)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun delPlayer(position: Int) {
        playerList.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun editPlayer(position: Int, player: Player) {
        playerList[position] = player
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClickPlayer(position: Int)
    }
}