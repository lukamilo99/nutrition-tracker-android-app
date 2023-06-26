package rs.raf.nutritiontracker.presentation.view.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            if (isUserLoggedIn()) {
                navController.navigate(R.id.homeFragment)
            } else {
                navController.navigate(R.id.loginFragment)
            }
            false
        }
        setContentView(binding.root)
    }

    private fun isUserLoggedIn(): Boolean {
        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        return !(username.isNullOrEmpty() || password.isNullOrEmpty())
    }
}