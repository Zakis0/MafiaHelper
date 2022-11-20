package com.example.mafiahelper.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mafiahelper.LOG
import com.example.mafiahelper.R
import com.example.mafiahelper.Timer
import com.example.mafiahelper.databinding.TimerItemBinding
import com.example.mafiahelper.timeNormalize

class TimerAdapter(val listener: Listener): RecyclerView.Adapter<TimerAdapter.TimerHolder>() {
    val timerList = ArrayList<Timer>()
    class TimerHolder(item: View): RecyclerView.ViewHolder(item) {
        val bindingClass = TimerItemBinding.bind(item)

        fun bind(timer: Timer, listener: Listener, position: Int) {
            bindingClass.timerView.text = timeNormalize(Timer(timer.minutes, timer.seconds))
            itemView.setOnLongClickListener {
                listener.onLongClickTimer(position)
                true
            }
            itemView.setOnClickListener {
                listener.onClickTimer(timer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
        return TimerHolder(view)
    }

    override fun onBindViewHolder(holder: TimerHolder, position: Int) {
        holder.bind(timerList[position], listener, position)
    }

    override fun getItemCount(): Int {
        return timerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTimer(timer: Timer) {
        timerList.add(timer)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun delTimer(position: Int) {
        timerList.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun editTimer(position: Int, timer: Timer) {
        timerList[position] = timer
        notifyDataSetChanged()
    }

    interface Listener {
        fun onLongClickTimer(position: Int)
        fun onClickTimer(timer: Timer)
    }
}