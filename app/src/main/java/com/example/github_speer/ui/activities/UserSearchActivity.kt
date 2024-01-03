package com.example.github_speer.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.github_speer.R
import com.example.github_speer.databinding.ActivityUserSearchBinding
import com.example.github_speer.ui.fragments.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUserSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_search)

        supportActionBar?.hide()

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, SearchFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            if(supportFragmentManager.backStackEntryCount == 1) finish()
            else supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}