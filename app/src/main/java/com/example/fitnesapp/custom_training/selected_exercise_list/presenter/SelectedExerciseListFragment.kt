package com.example.fitnesapp.custom_training.selected_exercise_list.presenter

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
import com.example.fitnesapp.databinding.FragmentSelectedExerciseListBinding
import com.example.fitnesapp.db.ExercisesModel
import com.example.fitnesapp.utils.Constants.CONST_10F
import com.example.fitnesapp.utils.Constants.CONST_128
import com.example.fitnesapp.utils.Constants.CONST_28F
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections

@AndroidEntryPoint
class SelectedExerciseListFragment : Fragment(), SelectedListExerciseAdapter.Listener {
    private var dayId = -1
    private lateinit var adapter: SelectedListExerciseAdapter
    private var binding: FragmentSelectedExerciseListBinding? = null
    private val _binding get() = binding!!
    private val model: SelectedExercisesListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedExerciseListBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        dayObserver()
        getArgs()
        _binding.addExercise.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("day_id", dayId)
            }
            findNavController().navigate(R.id.chooseExercisesFragment, bundle)
        }
    }

    private fun dayObserver() {
        model.listExercisesData.observe(viewLifecycleOwner) { list ->
            _binding.tvEmpty2.visibility = if (list.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            val count = "${getString(R.string.ex_count)} ${list.size}"
            _binding.tvCount.text = count
            adapter.submitList(list)
        }
    }

    private fun getArgs() {
        arguments.apply {
            dayId = this?.getInt("day_id") ?: -1
            val dayNumber = this?.getInt("day_number") ?: -1
            val title = "${getString(R.string.day)} $dayNumber"
            _binding.tv.text = title
            if (dayId != -1) {
                model.getExercises(dayId)
            }
        }
    }

    private fun initRcView() {
        _binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = SelectedListExerciseAdapter(this@SelectedExerciseListFragment)
            recyclerView.adapter = adapter
            createItemTouchHelper().attachToRecyclerView(recyclerView)
        }
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
                val tempList = ArrayList<ExercisesModel>(adapter.currentList)
                Collections.swap(tempList, startItem.adapterPosition, target.adapterPosition)
                adapter.submitList(tempList)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                adapter.removeAt(pos)
                onDelete(pos)
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
                val radius = CONST_28F // Установите желаемый радиус
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


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        updateDay()
    }

    private fun updateDay() {
        var exercises = ""
        adapter.currentList.forEach {
            exercises += ",${it.id}"
        }
        model.updateDay(exercises)
    }

    override fun onDelete(pos: Int) {
        val tempList = ArrayList<ExercisesModel>(adapter.currentList)
        tempList.removeAt(pos)
        adapter.submitList(tempList)
        if (tempList.isEmpty()) {
            _binding.tv.visibility = View.VISIBLE
        }
    }
}