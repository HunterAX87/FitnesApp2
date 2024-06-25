package com.example.fitnesapp.statistic.presenter.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DateSelectedListItemBinding
import com.example.fitnesapp.statistic.data.DateSelectorModel

class DateSelectorAdapter(private val listener: Listener) :
    ListAdapter<DateSelectorModel, DateSelectorAdapter.Holder>(Comparator()) {
    class Holder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view) {

        private val binding = DateSelectedListItemBinding.bind(view)

        fun bind(dateSelectorModel: DateSelectorModel) = with(binding) {
            itemMonth.text = dateSelectorModel.text

            if (dateSelectorModel.isSelected) {
                itemMonth.setTextColor(Color.WHITE)
                itemMonth.setBackgroundResource(R.drawable.date_selected)
            } else {
                itemMonth.setTextColor(Color.BLACK)
                itemMonth.setBackgroundResource(R.drawable.date_not_selected)
            }


            itemMonth.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.date_selected_list_item,
            parent,
            false
        )
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Comparator : DiffUtil.ItemCallback<DateSelectorModel>() {
        override fun areItemsTheSame(
            oldItem: DateSelectorModel,
            newItem: DateSelectorModel
        ): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(
            oldItem: DateSelectorModel,
            newItem: DateSelectorModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun onItemClick(index: Int)
    }
}