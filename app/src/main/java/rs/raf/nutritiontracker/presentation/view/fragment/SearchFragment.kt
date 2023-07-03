package rs.raf.nutritiontracker.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentSearchBinding
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.MealAdapter
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainContract.SharedViewModel by sharedViewModel<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecycleView()
    }

    private fun initRecycleView() {
        binding.rvSearchResults.layoutManager = LinearLayoutManager(context)
        binding.rvSearchResults.adapter = MealAdapter(
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
                            findNavController().navigate(R.id.mealDetailsFragment)
                        }
                    )
                    binding.rvSearchResults.adapter = adapter
                }
                is MealState.Error -> {

                }
                is MealState.Loading -> {

                }
                else -> {}
            }
        }

        sharedViewModel.selectedSearchCriteria.observe(viewLifecycleOwner) { criteria ->
            binding.tvSelectedCriteria.text = criteria
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
