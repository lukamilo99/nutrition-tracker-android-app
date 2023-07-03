package rs.raf.nutritiontracker.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.databinding.FragmentMealModifyBinding
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel
import timber.log.Timber

class MealModifyFragment : Fragment() {

    private var _binding: FragmentMealModifyBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainContract.SharedViewModel by sharedViewModel<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealModifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initButtons()
    }

    private fun initButtons() {
        binding.btnDeleteMeal.setOnClickListener {
            val mealState = sharedViewModel.mealDetails.value
            if (mealState is MealDetailsState.Success) {
                sharedViewModel.updateMealMultiField(getUpdatedMeal())
                sharedViewModel.deleteMeal(mealState.meal)
                Timber.e(mealState.meal.id + "AAAAAAAAAAAAAAAA")
            }
            findNavController().apply {
                popBackStack()
                popBackStack()
            }
        }

        binding.btnConfirmChanges.setOnClickListener {
            val mealState = sharedViewModel.mealDetails.value
            if (mealState is MealDetailsState.Success) {
                sharedViewModel.updateMealMultiField(getUpdatedMeal())
                sharedViewModel.updateMeal(mealState.meal)
            }
            findNavController().navigateUp()
        }
    }

    private fun initObservers() {
        sharedViewModel.mealDetails.observe(viewLifecycleOwner) { mealDetails ->
            when (mealDetails) {
                is MealDetailsState.Success -> {
                    binding.apply {
                        etMealName.setText(mealDetails.meal.name)
                        etCategory.setText(mealDetails.meal.category)
                        etArea.setText(mealDetails.meal.area)
                        etInstructions.setText(mealDetails.meal.instructions)
                        etType.setText(mealDetails.meal.type)
                        etYoutubeUrl.setText(mealDetails.meal.youtubeUrl ?: "")
                    }
                }
                else -> {}
            }
        }
    }

    private fun getUpdatedMeal(): MealDetailsView {
        return MealDetailsView(
            name = binding.etMealName.text.toString(),
            category = binding.etCategory.text.toString(),
            area = binding.etArea.text.toString(),
            instructions = binding.etInstructions.text.toString(),
            type = binding.etType.text.toString(),
            youtubeUrl = binding.etYoutubeUrl.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
