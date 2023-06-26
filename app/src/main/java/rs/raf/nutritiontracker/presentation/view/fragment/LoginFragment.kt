package rs.raf.nutritiontracker.presentation.view.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val sharedPreferences by inject<SharedPreferences>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val enteredUsername = binding.usernameEditText.text.toString()
            val enteredPassword = binding.passwordEditText.text.toString()

            if (enteredUsername == "test" && enteredPassword == "test") {
                with(sharedPreferences.edit()) {
                    putString("username", enteredUsername)
                    putString("password", enteredPassword)
                    apply()
                }
                findNavController().navigate(R.id.homeFragment)
            } else {
                // error
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
