package rs.raf.nutritiontracker.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentAdvancedSearchBinding
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealAdapter
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.StringState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel

class AdvancedSearchFragment : Fragment() {

    private var _binding: FragmentAdvancedSearchBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainContract.SharedViewModel by sharedViewModel<SharedViewModel>()
    private lateinit var categoriesAdapter: ArrayAdapter<String>
    private lateinit var regionsAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdvancedSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchApiDb.text = "API"
        sharedViewModel.fetchRegionNames()
        sharedViewModel.fetchCategoryNames()
        initAutoComplete()
        initRecycleView()
        initObservers()
        initButton()
        initSpinner()
        initSwitch()
    }

    private fun initAutoComplete() {
        categoriesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mutableListOf())
        regionsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mutableListOf())

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_categories -> binding.etSearch.setAdapter(categoriesAdapter)
                R.id.radio_regions -> binding.etSearch.setAdapter(regionsAdapter)
            }
        }
    }

    private fun initRecycleView() {
        binding.rvAdvancedSearchResults.layoutManager = LinearLayoutManager(context)
        binding.rvAdvancedSearchResults.adapter = MealAdapter(
            meals = listOf(),
            onFetch = { mealId ->
                sharedViewModel.fetchMealDetailsById(mealId)
                findNavController().navigate(R.id.mealDetailsFragment)
            },
            onGet = { mealId ->
                sharedViewModel.getMealDetailsById(mealId.toInt())
                findNavController().navigate(R.id.mealDetailsFragment)
            }
        )
    }

    private fun initObservers() {
        sharedViewModel.meals.observe(viewLifecycleOwner) {
            when (it) {
                is MealState.Success -> {
                    val adapter = MealAdapter(
                        meals = it.meals,
                        onFetch = { mealId ->
                            sharedViewModel.fetchMealDetailsById(mealId)
                            findNavController().navigate(R.id.mealDetailsFragment)
                        },
                        onGet = { mealId ->
                            sharedViewModel.getMealDetailsById(mealId.toInt())
                            sharedViewModel.mealDetails.value
                            findNavController().navigate(R.id.mealDetailsFragment)
                        }
                    )
                    binding.rvAdvancedSearchResults.adapter = adapter
                }
                is MealState.Error -> {
                }
                is MealState.Loading -> {
                }
                else -> {}
            }
        }

        sharedViewModel.categoryNames.observe(viewLifecycleOwner) {
            when (it) {
                is StringState.Success -> {
                    categoriesAdapter.clear()
                    categoriesAdapter.addAll(it.names)
                }

                is StringState.Error -> {

                }

                is StringState.Loading -> {

                }

                else -> {}
            }
        }

        sharedViewModel.regionNames.observe(viewLifecycleOwner) {
            when (it) {
                is StringState.Success -> {
                    regionsAdapter.clear()
                    regionsAdapter.addAll(it.names)
                }

                is StringState.Error -> {

                }

                is StringState.Loading -> {
                }

                else -> {}
            }
        }
    }

    private fun initButton() {
        binding.btnSearch.setOnClickListener {
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            val switchChecked = binding.switchApiDb.isChecked
            val editTextValue = binding.etSearch.text.toString()

            when(selectedRadioButtonId) {
                R.id.radio_categories -> {
                    if(switchChecked) {
                        sharedViewModel.getMealsByCategory(editTextValue)
                    } else {
                        sharedViewModel.fetchMealsByCategory(editTextValue)
                    }
                }
                R.id.radio_regions -> {
                    if(switchChecked) {
                        sharedViewModel.getMealsByRegion(editTextValue)
                    } else {
                        sharedViewModel.fetchMealsByRegion(editTextValue)
                    }
                }
                R.id.radio_ingredients -> {
                    if(switchChecked) {
                        sharedViewModel.getMealsByMainIngredient(editTextValue)
                    } else {
                        sharedViewModel.fetchMealsByMainIngredient(editTextValue)
                    }
                }
            }
        }
    }

    private fun initSpinner() {
        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (parent.getItemAtPosition(position).toString()) {
                    "Sort by name ascending" -> sharedViewModel.sortMealsByNameAscending()
                    "Sort by name descending" -> sharedViewModel.sortMealsByNameDescending()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun initSwitch() {
        binding.switchApiDb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.switchApiDb.text = "Menu"
            } else {
                binding.switchApiDb.text = "API"
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
