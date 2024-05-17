package com.example.fitnesapp.statistic.presenter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.FragmentStatisticBinding
import com.example.fitnesapp.db.WeightModel
import com.example.fitnesapp.statistic.data.DateSelectorModel
import com.example.fitnesapp.statistic.presenter.adapters.DateSelectorAdapter
import com.example.fitnesapp.statistic.utils.UtilsArray
import com.example.fitnesapp.utils.DialogManager
import com.example.fitnesapp.utils.TimeUtils
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class StatisticFragment : Fragment() {

    private lateinit var yearAdapter: DateSelectorAdapter
    private lateinit var monthAdapter: DateSelectorAdapter
    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!
    private val model: StatisticViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.weightEditButton.setOnClickListener {
            DialogManager.showWeightDialog(requireContext(),
                object : DialogManager.WeightListener {
                    override fun onClick(weight: String) {
                        if (weight.isEmpty()) {
                            Toast.makeText(
                                requireContext(), "Weight field is Empty!", Toast.LENGTH_SHORT
                            ).show()
                        }
                        model.saveWeight(weight.toFloat().toInt())
                    }

                }
            )
        }
        model.getYearList()
        observeYearList()
        weightListObserver()
        initRcView()
        calendarDateObserver()
        statisticObsorver()
        onClalendarClick()
        model.getStatisticEvents()
        model.getStatisticByDate(TimeUtils.getCurrentDate())

    }

    private fun observeYearList() {
        model.yearListData.observe(viewLifecycleOwner) {
            if(it.isEmpty()) return@observe
            val yearTemp = ArrayList<DateSelectorModel>(it)
            yearTemp[yearTemp.size - 1] = yearTemp[yearTemp.size - 1].copy(isSelected = true)
            model.year = yearTemp[yearTemp.size - 1].text.toInt()
            yearAdapter.submitList(yearTemp)
            model.getWeightByYearAndMonth()
        }
    }

    private fun initRcView() = with(binding) {

        yearAdapter = DateSelectorAdapter(object : DateSelectorAdapter.Listener {
            override fun onItemClick(index: Int) {
                model.year = yearAdapter.currentList[index].text.toInt()
                setSelectedDateForWeight(index, yearAdapter)
            }
        })
        monthAdapter = DateSelectorAdapter(object : DateSelectorAdapter.Listener {
            override fun onItemClick(index: Int) {
                model.month = index
                setSelectedDateForWeight(index, monthAdapter)
            }
        })
        dateWeightSelector.yearRcView.adapter = yearAdapter
        dateWeightSelector.monthRcView.adapter = monthAdapter

        val cv = Calendar.getInstance()
        val monthTemp = ArrayList<DateSelectorModel>(UtilsArray.monthList)
        monthTemp[cv.get(Calendar.MONTH)] =
            monthTemp[cv.get(Calendar.MONTH)].copy(isSelected = true)
        monthAdapter.submitList(monthTemp)
    }

    private fun weightListObserver() {
        model.weightListData.observe(viewLifecycleOwner) { list ->
            setChartData(list)
        }
    }


    private fun setSelectedDateForWeight(index: Int, adapter: DateSelectorAdapter) {
        val list = ArrayList<DateSelectorModel>(adapter.currentList)

        for (i in list.indices) {
            list[i] = list[i].copy(isSelected = false)
        }
        list[index] = list[index].copy(isSelected = true)
        adapter.submitList(list)
        model.getWeightByYearAndMonth()
    }


    private fun calendarDateObserver() {
        model.eventListData.observe(viewLifecycleOwner) {
            binding.cView.setEvents(it)
        }
    }

    private fun onClalendarClick() {
        binding.cView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                model.getStatisticByDate(TimeUtils.getDateFromCalendar(eventDay.calendar))
            }

        })
    }

    private fun statisticObsorver() {
        model.statisticData.observe(viewLifecycleOwner) {
            binding.apply {
                time.text = TimeUtils.getWorkautTime(
                    it.workautTime.toLong() * 1000
                )
                kcal.text = it.kcal.toString()
                date.text = if (TimeUtils.getCurrentDate() == it.date) {
                    "Today"
                } else {
                    it.date
                }
            }
        }
    }

    private fun setChartData(tempWeightList: List<WeightModel>) = with(binding) {
        val weightList = ArrayList<BarEntry>()

        for (i in 0..30) {
            val list = tempWeightList.filter {
                i == it.day - 1
            }
            weightList.add(
                BarEntry(
                    i.toFloat(),
                    if (list.isEmpty()) 0f else list[0].weight.toFloat()
                )
            )
        }

        val set: BarDataSet
        if (barChart.data != null && barChart.data.dataSetCount > 0) {

            set = barChart.data.getDataSetByIndex(0) as BarDataSet
            set.values = weightList
            set.label = "${model.year}/${UtilsArray.monthList[model.month].text}"
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()

        } else {

            set = BarDataSet(weightList, "${model.year}/${UtilsArray.monthList[model.month].text}")
            set.color = Color.GREEN
            set.valueTextColor = Color.GRAY
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set)
            val barData = BarData(dataSets)
            barData.setValueTextSize(10f)
            barChart.data = barData

        }
        barChart.invalidate()
        barChartSettings()
    }

    private fun barChartSettings() = with(binding) {

        barChart.description.isEnabled = false
        barChart.legend.apply {
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            textColor = Color.WHITE
        }
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.axisMinimum = 0f
        barChart.xAxis.textColor = Color.WHITE
        barChart.axisLeft.textColor = Color.WHITE
        barChart.axisRight.isEnabled = false
        barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            labelCount = 10
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return (value + 1).toInt().toString()
                }
            }
        }
    }
}