package com.example.fitnesapp.settings.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.FragmentSettingsBinding
import com.example.fitnesapp.utils.DialogManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var _binding: FragmentSettingsBinding
    private val binding get() = _binding
    private val model: SettingsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            bCostomTraining.setOnClickListener {
                bCostomTraining.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.pressed_button
                    )
                )
                findNavController().navigate(R.id.customDaysListFragment)
            }


            bResetAllData.setOnClickListener {
                DialogManager.showDialog(requireContext(),
                    R.string.a_reset_all_data, object : DialogManager.Listener {
                        override fun onClick() {
                            model.clearAllData()
                        }
                    }
                )
            }

            bResetProgress.setOnClickListener {
                DialogManager.showDialog(requireContext(),
                    R.string.a_reset_progress, object : DialogManager.Listener {
                        override fun onClick() {
                            model.clearProgress()
                        }
                    }
                )
            }

            bResetWeight.setOnClickListener {
                DialogManager.showDialog(requireContext(),
                    R.string.a_reset_weight, object : DialogManager.Listener {
                        override fun onClick() {
                            model.clearWeight()
                        }
                    }
                )
            }

            bResetStatistic.setOnClickListener {
                DialogManager.showDialog(requireContext(),
                    R.string.a_reset_statistic, object : DialogManager.Listener {
                        override fun onClick() {
                            model.clearStatistic()
                        }
                    }
                )
            }
        }
    }


}