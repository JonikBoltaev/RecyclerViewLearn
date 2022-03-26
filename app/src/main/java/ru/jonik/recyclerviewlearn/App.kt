package ru.jonik.recyclerviewlearn

import android.app.Application
import ru.jonik.recyclerviewlearn.model.UsersService

// Чтобы сделать UsersService - singleTon
class App : Application() {
    val usersService: UsersService = UsersService()
}