package rs.raf.nutritiontracker.presentation.view.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val sharedPreferences by inject<SharedPreferences>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            isUserLoggedIn()
            false
        }

        binding.navHostFragment.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                navController = findNavController(R.id.nav_host_fragment)
                binding.navHostFragment.viewTreeObserver.removeOnGlobalLayoutListener(this)
                if (isUserLoggedIn()) {
                    navController.navigate(R.id.homeFragment)
                } else {
                    navController.navigate(R.id.loginFragment)
                }
            }
        })
        val view = binding.root
        setContentView(view)
    }

    private fun isUserLoggedIn(): Boolean {
        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        val email = sharedPreferences.getString("email", null)

        return !(username.isNullOrEmpty() || password.isNullOrEmpty() || email.isNullOrEmpty())
    }
}