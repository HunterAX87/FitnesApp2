package com.example.fitnesapp.custom_training.choose_exercises.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.FragmentChooseExercisesBinding
import com.example.fitnesapp.db.ExercisesModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChooseExercisesFragment : Fragment(), ChooseExercisesAdapter.Listener {
    private var newExercises = ""
    private lateinit var adapter: ChooseExercisesAdapter
    private lateinit var binding: FragmentChooseExercisesBinding
    private val _binding get() = binding
    private val model: ChooseExerciseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseExercisesBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.bDone.setOnClickListener {
            model.updateDay(newExercises)
            findNavController().popBackStack()
        }
        _binding.tv.text = getString(R.string.all_exercises)
        getArgs()
        initRcView()
        exerciseListObserver()
        model.getAllExercises()
    }

    private fun getArgs() {
        arguments.apply {
            val dayId = this?.getInt("day_id") ?: -1
            if (dayId != -1) {
                model.getDayById(dayId)
            }
        }
    }

    private fun initRcView() = with(_binding) {
        rcView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChooseExercisesAdapter(this@ChooseExercisesFragment)
        rcView.adapter = adapter
    }

    private fun exerciseListObserver() {
        model.exerciseListData.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }



    override fun onClick(exercise: ExercisesModel) {
        if (exercise.id != -1) {
            newExercises += ",${exercise.id}"
        }
        val count = newExercises.split(",").size - 1
        val countText = "${getString(R.string.selected_exercise_count)} $count"
        _binding.tvCount.text = countText
        Snackbar.make(
            _binding.rcView,
            "${getString(R.string.exercises_e)} ${exercise.name} ${getString(R.string.added)}",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}