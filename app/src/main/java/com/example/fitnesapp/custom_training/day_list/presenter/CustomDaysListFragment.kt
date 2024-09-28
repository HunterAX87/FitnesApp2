package com.example.fitnesapp.custom_training.day_list.presenter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.FragmentCustomDaysListBinding
import com.example.fitnesapp.db.DayModel
import com.example.fitnesapp.training.utils.TrainingUtils
import com.example.fitnesapp.utils.Constants.CONST_10F
import com.example.fitnesapp.utils.Constants.CONST_128
import com.example.fitnesapp.utils.Constants.CONST_35F
import com.example.fitnesapp.utils.DialogManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections

@AndroidEntryPoint
class CustomDaysListFragment : Fragment(), CustomDaysAdapter.Listener {

    private lateinit var daysAdapter: CustomDaysAdapter
    private var _binding: FragmentCustomDaysListBinding? = null
    private val binding get() = _binding!!
    private val model: CustomDaysListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomDaysListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bAddNewDay.setOnClickListener {
            model.insertDay(
                DayModel(
                    null,
                    "",
                    TrainingUtils.CUSTOM,
                    0,
                    0,
                    false
                )
            )
        }
        initRcView()
        daysListObserver()
        binding.tv.text = getString(R.string.custom_day_list)
    }

    private fun initRcView() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            daysAdapter = CustomDaysAdapter(this@CustomDaysListFragment)
            rcView.adapter = daysAdapter
            createItemTouchHelper().attachToRecyclerView(rcView)

        }
    }

    private fun daysListObserver() {
        model.daysListData.observe(viewLifecycleOwner) { list ->
            daysAdapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(day: DayModel) {
        val bundle = Bundle().apply {
            putInt("day_id", day.id ?: -1)
            putInt("day_number", day.dayNumber)
        }
        findNavController().navigate(R.id.selectedExerciseListFragment, bundle)
    }

    //Блок для перемещения элементов со списка
    private fun createItemTouchHelper(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                startItem: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val tempList = ArrayList<DayModel>(daysAdapter.currentList)
                Collections.swap(tempList, startItem.adapterPosition, target.adapterPosition)
                daysAdapter.submitList(tempList)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val day = daysAdapter.currentList[viewHolder.adapterPosition]
                daysAdapter.removeAt(viewHolder.adapterPosition)
                onDelete(day) // Вызов функции onDelete при свайпе элемента
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val color = Color.RED // Измените цвет на ваш предпочтительный
                val paint = Paint()
                paint.color = color
                paint.alpha = CONST_128 // Регулируйте прозрачность
                val radius = CONST_35F // Установите желаемый радиус
                val rect = RectF(
                    viewHolder.itemView.left.toFloat(),
                    viewHolder.itemView.top.toFloat() + CONST_10F, // Отключаем прорисовку на top
                    viewHolder.itemView.right.toFloat(),
                    viewHolder.itemView.bottom.toFloat() - CONST_10F // Отключаем прорисовку на bottom
                )
                c.drawRoundRect(rect, radius, radius, paint)
            }
        }
        )
    }


    override fun onDelete(day: DayModel) {
        DialogManager.showDialog(
            requireContext(),
            R.string.delete_day,
            object : DialogManager.Listener {
                override fun onClick() {
                    model.deleteDay(day)

                }
            }
        )
    }
}