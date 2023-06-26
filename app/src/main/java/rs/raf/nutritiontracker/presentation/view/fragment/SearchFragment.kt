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
        binding.rvSearchResults.adapter = MealAdapter(listOf()) { mealId ->
            sharedViewModel.getMealDetailsById(mealId)
            findNavController().navigate(R.id.mealDetailsFragment)
        }
    }

    private fun initObservers() {
        sharedViewModel.meals.observe(viewLifecycleOwner) {
            when (it) {
                is MealState.Success -> {
                    val adapter = MealAdapter(it.meals) { mealId ->
                        sharedViewModel.getMealDetailsById(mealId)
                        findNavController().navigate(R.id.mealDetailsFragment)
                    }
                    binding.rvSearchResults.adapter = adapter
                }

                is MealState.Error -> {
                    // handle error, access the error message with it.message
                    // you can show an error message to the user
                }

                is MealState.Loading -> {
                    // handle loading state
                    // you can show a loading spinner
                }

                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
