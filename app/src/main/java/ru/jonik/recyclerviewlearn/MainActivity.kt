package ru.jonik.recyclerviewlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.jonik.recyclerviewlearn.databinding.ActivityMainBinding
import ru.jonik.recyclerviewlearn.model.User
import ru.jonik.recyclerviewlearn.model.UsersListener
import ru.jonik.recyclerviewlearn.model.UsersService
import ru.jonik.recyclerviewlearn.screens.UsersDetailsFragment
import ru.jonik.recyclerviewlearn.screens.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UsersListFragment())
                .commit()
        }
    }

    override fun showDetails(user: User) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, UsersDetailsFragment.newInstance(user.id))
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}