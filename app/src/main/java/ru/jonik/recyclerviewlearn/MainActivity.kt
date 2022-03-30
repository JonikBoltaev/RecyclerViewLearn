package ru.jonik.recyclerviewlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.jonik.recyclerviewlearn.databinding.ActivityMainBinding
import ru.jonik.recyclerviewlearn.model.User
import ru.jonik.recyclerviewlearn.model.UsersListener
import ru.jonik.recyclerviewlearn.model.UsersService
import ru.jonik.recyclerviewlearn.screens.UsersListFragment

class MainActivity : AppCompatActivity() {

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
}