package ru.jonik.recyclerviewlearn.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.jonik.recyclerviewlearn.model.User
import ru.jonik.recyclerviewlearn.model.UsersListener
import ru.jonik.recyclerviewlearn.model.UsersService

class UsersListViewModel(private val usersService: UsersService) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val listener: UsersListener = {
        _users.value = it
    }

    init {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        usersService.removeListener(listener)
    }

    // Загрузка пользователей
    fun loadUsers() {
        usersService.addListener(listener)
    }

    // Перемещение пользователя
    fun moveUsers(user: User, moveBy: Int) {
        usersService.moveUser(user, moveBy)
    }

    // Удаление пользователя
    fun deleteUsers(user: User) {
        usersService.deleteUser(user)
    }
}