package ru.jonik.recyclerviewlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.jonik.recyclerviewlearn.databinding.ActivityMainBinding
import ru.jonik.recyclerviewlearn.model.User
import ru.jonik.recyclerviewlearn.model.UsersListener
import ru.jonik.recyclerviewlearn.model.UsersService

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        usersService.addListener(usersListener)
    }

    // Избежать утечку памяти
    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }

    private val usersListener: UsersListener = {
        adapter.users = it
    }
}