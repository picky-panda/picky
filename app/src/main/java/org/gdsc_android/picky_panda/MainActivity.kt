package org.gdsc_android.picky_panda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.gdsc_android.picky_panda.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationView()
    }

    fun setBottomNavigationView() {
        binding.bottomNavigationView.selectedItemId = R.id.fragmentHome
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.fragmentHome -> HomeFragment()
                R.id.fragmentAdd -> AddFragment()
                else -> MyPageFragment()
            }
            replaceFragment(fragment)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment, "")
            .commit()

    }
}