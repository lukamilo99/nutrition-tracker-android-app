package rs.raf.nutritiontracker.presentation.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentHomeBinding
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.nutritiontracker.presentation.view.states.CategoryState
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainContract.SharedViewModel by sharedViewModel<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel.fetchCategories()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecycleView()
        initButton()
    }

    private fun initButton() {
        binding.btnSearch.setOnClickListener {
            showSearchOptionsDialog()
        }
    }

    private fun initRecycleView() {
        binding.rvCategories.layoutManager = LinearLayoutManager(context)
        binding.rvCategories.adapter = CategoryAdapter(listOf()) { category ->
            sharedViewModel.fetchMealsByCategory(category.strCategory)
            sharedViewModel.setSelectedSearchCriteria("Category: ${category.strCategory}")
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private fun initObservers() {
        sharedViewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is CategoryState.Success -> {
                    val adapter = CategoryAdapter(it.categories) { category ->
                        sharedViewModel.fetchMealsByCategory(category.strCategory)
                        sharedViewModel.setSelectedSearchCriteria("Category: ${category.strCategory}")
                        findNavController().navigate(R.id.searchFragment)
                    }
                    binding.rvCategories.adapter = adapter
                    binding.rvCategories.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                }

                is CategoryState.Error -> {

                }

                is CategoryState.Loading -> {

                }

                else -> {}
            }
        }
    }

    private fun showSearchOptionsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Select Search Option")
            .setItems(arrayOf("By Name", "By Ingredient")) { _, which ->
                val selectedCriteria = binding.searchEditText.text.toString()
                when (which) {

                    0 -> {
                        sharedViewModel.fetchMealsByName(selectedCriteria)
                        sharedViewModel.setSelectedSearchCriteria("Name: $selectedCriteria")
                        findNavController().navigate(R.id.searchFragment)
                    }
                    1 -> {
                        sharedViewModel.fetchMealsByMainIngredient(binding.searchEditText.text.toString())
                        sharedViewModel.setSelectedSearchCriteria("Ingredient: $selectedCriteria")
                        findNavController().navigate(R.id.searchFragment)
                    }
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
