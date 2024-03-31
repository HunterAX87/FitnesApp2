package com.example.fitnesapp.adapteers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DaysListItemBinding
import com.example.fitnesapp.db.DayModel

class DaysAdapter(var listener:Listener): ListAdapter<DayModel,DaysAdapter.DayHolder> (MyComparator()){


    class DayHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding= DaysListItemBinding.bind(view)


        fun setData(day: DayModel, listener:Listener)= with(binding){
            val name= root.context.getString(R.string.day)+ " ${adapterPosition +1}"
            tvName.text= name
            val exCounter= day.exercises.split(",").size.toString()+ " " + root.context.getString(R.string.exercises)
            tvExCounter.text=exCounter
            checkBoxImage.visibility= if(day.isDone) View.VISIBLE else View.INVISIBLE
            itemView.setOnClickListener { listener.onClick(day.copy(dayNumber = adapterPosition+1)) }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.days_list_item, parent, false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }


    class MyComparator:DiffUtil.ItemCallback<DayModel>(){
        override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem==newItem
        }

    }

    interface Listener{
        fun onClick(day: DayModel)
    }
}