package com.example.universityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.universityapp.Fragments.main_page
import com.example.universityapp.Fragments.news_page
import com.example.universityapp.Fragments.taechers_page
import com.example.universityapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val fList  = listOf(
        main_page,
        news_page,
        taechers_page
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.vp, main_page.newInstance()).commit()
        binding.navigationMenu.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.main -> {
                    binding.toolbar.title = getString(R.string.main)
                    getFragment(R.id.vp, main_page.newInstance())
                }
                R.id.news -> {
                    binding.toolbar.title = getString(R.string.news)
                    getFragment(R.id.vp, news_page.newInstance())
                }
                R.id.teachers -> {
                    binding.toolbar.title = getString(R.string.taechers)
                    getFragment(R.id.vp, taechers_page.newInstance())
                }
            }
        }
    }

    fun getFragment(vp: Int, fa: Fragment){
        supportFragmentManager.beginTransaction().replace(vp, fa).commit()
    }
}