package rs.raf.nutritiontracker.presentation.view.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentMealMenuBinding
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MealMenuFragment: Fragment() {

    private var _binding: FragmentMealMenuBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainContract.SharedViewModel by sharedViewModel<SharedViewModel>()
    private val mealTypes = arrayOf("Breakfast", "Lunch", "Dinner", "Snack")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initButton()

        binding.mealPreparationDateTextView.setOnClickListener { showDatePickerDialog() }
        binding.mealTypeTextView.setOnClickListener { showMealTypePickerDialog() }
        binding.mealPreparationDateTextView.text = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault()).format(Date())
    }

    private fun initButton() {
        binding.saveMealButton.setOnClickListener {
            val mealState = sharedViewModel.mealDetails.value
            if (mealState is MealDetailsState.Success) {
                sharedViewModel.insertMeal(mealState.meal)
                findNavController().navigateUp()
            }
        }
    }

    private fun initObservers() {
        sharedViewModel.mealDetails.observe(viewLifecycleOwner) {
            when(it) {
                is MealDetailsState.Success -> {
                    binding.apply {
                        mealNameTextView.text = it.meal.name
                        Glide.with(this@MealMenuFragment).load(it.meal.mealThumb).into(mealImageView)
                    }
                }

                else -> {}
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            sharedViewModel.updateMealPreparationDate(calendar.time)
            binding.mealPreparationDateTextView.text = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault()).format(calendar.time)
        }
        DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showMealTypePickerDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Choose type")
            .setItems(mealTypes) { _, which ->
                val selectedMealType = mealTypes[which]
                sharedViewModel.updateMealType(selectedMealType)
                binding.mealTypeTextView.text = selectedMealType
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
