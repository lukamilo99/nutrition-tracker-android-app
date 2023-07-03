package rs.raf.nutritiontracker.presentation.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentStatisticsBinding
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel.getMealsFromLastWeek()
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barChart = binding.barChart
        pieChart = binding.pieChart

        sharedViewModel.mealsStats.observe(viewLifecycleOwner) { it ->
            when(it) {
                is MealDetailsState.Error -> TODO()
                MealDetailsState.Loading -> TODO()
                is MealDetailsState.Success -> TODO()
                is MealDetailsState.SuccessList -> {
                    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    val mealsByDate = it.meals.groupBy { meal ->
                        format.format(meal.creationDate)
                    }

                    val barData = mutableListOf<BarEntry>()
                    for (i in 0 until 7) {
                        val calendar = Calendar.getInstance()
                        calendar.add(Calendar.DAY_OF_YEAR, -i)
                        val date = format.format(calendar.time)
                        val count = mealsByDate[date]?.size ?: 0
                        barData.add(BarEntry(i.toFloat(), count.toFloat()))
                    }

                    val pieData = it.meals.groupBy { it.type }
                        .mapValues { it.value.size }
                        .map { PieEntry(it.value.toFloat(), it.key) }

                    showBarChart(barData)
                    showPieChart(pieData)
                }
            }
        }
    }

    private fun showBarChart(data: List<BarEntry>) {
        val colors = mutableListOf(Color.WHITE)
        val dataSet = BarDataSet(data, "Number of meals").apply {
            setColors(colors)
            valueTextColor = Color.WHITE
            valueTextSize = 16f
        }
        val barData = BarData(dataSet)

        with(barChart) {
            this.data = barData
            xAxis.apply {
                granularity = 1f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val daysAgo = value.toInt()
                        return when (daysAgo) {
                            0 -> "Today"
                            1 -> "1 day ago"
                            else -> "$daysAgo days ago"
                        }
                    }
                }
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textColor = Color.WHITE
            }
            legend.textColor = Color.WHITE
            axisLeft.apply {
                setDrawGridLines(false)
                setDrawLabels(false)
            }
            axisRight.isEnabled = false
            description.isEnabled = false
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            animateY(1400, Easing.EaseInOutQuad)
            invalidate()
        }
    }

    private fun showPieChart(data: List<PieEntry>) {
        val colors = mutableListOf(
            ContextCompat.getColor(requireContext(), R.color.red),
            ContextCompat.getColor(requireContext(), R.color.green),
            ContextCompat.getColor(requireContext(), R.color.blue),
            ContextCompat.getColor(requireContext(), R.color.yellow)
        )

        val dataSet = PieDataSet(data, "Meal types").apply {
            setColors(colors)
            valueTextColor = Color.WHITE
            valueTextSize = 16f
        }

        val pieData = PieData(dataSet)

        with(pieChart) {
            this.data = pieData
            description.isEnabled = false
            setUsePercentValues(true)
            setEntryLabelTextSize(16f)
            holeRadius = 30f
            transparentCircleRadius = 35f
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            setDrawCenterText(true)
            centerText = "Meal types"
            setCenterTextSize(20f)
            animateY(1400, Easing.EaseInOutQuad)
            invalidate()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
