package rs.raf.nutritiontracker.presentation.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.databinding.FragmentMealDetailsBinding
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel

class MealDetailsFragment : Fragment() {

    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainContract.SharedViewModel by sharedViewModel<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        sharedViewModel.mealDetails.observe(viewLifecycleOwner) {
            when (it) {
                is MealDetailsState.Success -> {
                    binding.apply {
                        tvMealName.text = it.meal.name
                        tvCategory.text = it.meal.category
                        tvArea.text = it.meal.area
                        tvInstructions.text = it.meal.instructions
                        Glide.with(requireContext())
                            .load(it.meal.mealThumb)
                            .into(ivMealImage)

                        if(it.meal.isSaved) {
                            btnSaveMeal.visibility = View.GONE
                            tvPreparationDate.visibility = View.VISIBLE
                            tvPreparationDate.text = it.meal.preparationDate.toString()
                            tvType.visibility = View.VISIBLE
                            tvType.text = it.meal.type
                            labelType.visibility = View.VISIBLE
                            labelPreparationDate.visibility = View.VISIBLE
                            btnModifyMeal.visibility = View.VISIBLE
                            btnModifyMeal.setOnClickListener {
                                findNavController().navigate(R.id.modifyMealFragment)
                            }
                        } else {
                            btnModifyMeal.visibility = View.GONE
                            btnSaveMeal.visibility = View.VISIBLE
                            tvPreparationDate.visibility = View.GONE
                            tvType.visibility = View.GONE
                            labelType.visibility = View.GONE
                            labelPreparationDate.visibility = View.GONE
                            btnSaveMeal.setOnClickListener {
                                findNavController().navigate(R.id.mealMenuFragment)
                            }
                        }

                        if(it.meal.youtubeUrl != null) {
                            val url = it.meal.youtubeUrl
                            btnYoutubeLink.visibility = View.VISIBLE
                            btnYoutubeLink.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(intent)
                            }
                        } else {
                            btnYoutubeLink.visibility = View.GONE
                        }

                        val ingredients = StringBuilder()
                        val measures = StringBuilder()
                        for (i in 1..20) {
                            val ingredient = getIngredient(it.meal, i)
                            val measure = getMeasure(it.meal, i)
                            if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                                ingredients.append("$ingredient\n")
                                measures.append("$measure\n")
                            }
                        }
                        tvIngredients.text = ingredients.toString()
                        tvMeasures.text = measures.toString()
                    }

                }

                is MealDetailsState.Error -> {

                }

                is MealDetailsState.Loading -> {

                }

                else -> {}
            }
        }
    }

    private fun getIngredient(meal: MealDetailsView, index: Int): String? {
        return meal.ingredientsWithMeasurements.getOrNull(index)?.ingredientName
    }

    private fun getMeasure(meal: MealDetailsView, index: Int): String? {
        return meal.ingredientsWithMeasurements.getOrNull(index)?.quantity
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}