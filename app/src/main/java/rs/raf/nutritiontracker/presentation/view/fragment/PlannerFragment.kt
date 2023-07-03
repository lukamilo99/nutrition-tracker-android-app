package rs.raf.nutritiontracker.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.databinding.FragmentPlannerBinding
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealDetailsAdapter
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel

class PlannerFragment : Fragment() {

    private var _binding: FragmentPlannerBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainContract.SharedViewModel by sharedViewModel<SharedViewModel>()
    private var currentMealIndex = 0
    private var currentDayIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.getAllMeals()
        initObserver()
        initButton()
    }

    private fun initObserver() {
        sharedViewModel.allMeals.observe(viewLifecycleOwner) { mealState ->
            when (mealState) {
                is MealDetailsState.SuccessList -> initAdapter(mealState.meals)
                is MealDetailsState.Error -> initAdapter(listOf())
                MealDetailsState.Loading -> {}
                is MealDetailsState.Success -> initAdapter(listOf(mealState.meal))
                null -> initAdapter(listOf())
            }
        }
    }

    private fun initAdapter(mealList: List<MealDetailsView>) {
        val mealAdapter = MealDetailsAdapter(requireContext(), mealList)

        setupAutoCompleteTextView(binding.breakfastInput, mealAdapter, 0)
        setupAutoCompleteTextView(binding.lunchInput, mealAdapter, 1)
        setupAutoCompleteTextView(binding.dinnerInput, mealAdapter, 2)
        setupAutoCompleteTextView(binding.snackInput, mealAdapter, 3)
    }

    private fun initButton() {
        binding.buttonNextDay.setOnClickListener {
            currentMealIndex = 0
            currentDayIndex++
            binding.dayTextView.text = getDayName(currentDayIndex)
        }
    }

    private fun setupAutoCompleteTextView(autoCompleteTextView: AutoCompleteTextView, adapter: ArrayAdapter<MealDetailsView>, index: Int) {
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedMeal = adapter.getItem(position)
            sharedViewModel.insertMealIntoPlan(selectedMeal!!, index)
        }
    }

    private fun getDayName(dayIndex: Int): String {
        return when(dayIndex % 7) {
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            6 -> "Sunday"
            else -> "Unknown"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

