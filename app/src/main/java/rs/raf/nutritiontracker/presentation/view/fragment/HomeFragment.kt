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
import org.koin.androidx.viewmodel.ext.android.viewModel
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecycleView()
        initButton()
        sharedViewModel.getCategories()
    }

    private fun initButton() {
        binding.btnSearch.setOnClickListener {
            showSearchOptionsDialog()
            binding.btnSearch.setOnClickListener {
                findNavController().navigate(R.id.searchFragment)
            }
        }
    }

    private fun initRecycleView() {
        binding.rvCategories.layoutManager = LinearLayoutManager(context)
        binding.rvCategories.adapter = CategoryAdapter(listOf())
    }

    private fun initObservers() {
        sharedViewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is CategoryState.Success -> {
                    val adapter = CategoryAdapter(it.categories)
                    binding.rvCategories.adapter = adapter
                    binding.rvCategories.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                }

                is CategoryState.Error -> {
                    // handle error, access the error message with it.message
                    // you can show an error message to the user
                }

                is CategoryState.Loading -> {
                    // handle loading state
                    // you can show a loading spinner
                }

                else -> {}
            }
        }
    }

    private fun showSearchOptionsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Select Search Option")
            .setItems(arrayOf("By Name", "By Ingredient")) { _, which ->
                when (which) {
                    0 -> sharedViewModel.getMealByName(binding.searchEditText.text.toString())
                    1 -> sharedViewModel.getMealByMainIngredient(binding.searchEditText.text.toString())
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
