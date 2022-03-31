package ru.jonik.recyclerviewlearn.model

import com.github.javafaker.Faker
import ru.jonik.recyclerviewlearn.UserNotFoundException
import java.util.*

// Возвращает список обновленных пользователей
typealias UsersListener = (users: List<User>) -> Unit

//Получает данные и может манипулировать ими
class UsersService {
    // Получаем список пользователей
    private var users = mutableListOf<User>()

    // Переменная, в которой складываются все слушатели
    private val listeners = mutableSetOf<UsersListener>()

    //Генерируем список пользователей с помощью Glide
    init {
        IMAGES.shuffle()
        val faker = Faker.instance()
        users = (1..100).map {
            User(
                id = it.toLong(),
                name = faker.name().name(),
                company = faker.company().name(),
                photo = IMAGES[it % IMAGES.size]
            )
        }.toMutableList()
    }

    // Получение списка пользователей
    fun getUsers(): List<User> {
        return users
    }

    // Удаление пользователя
    fun deleteUser(user: User) {
        val indexToDelete = users.indexOfFirst { it.id == user.id }
        if (indexToDelete != -1) {
            users = ArrayList(users)
            users.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun getById(id: Long): UserDetails {
        val user = users.firstOrNull { it.id == id } ?: throw UserNotFoundException()
        return UserDetails(
            user = user,
            details = Faker.instance().lorem().paragraphs(3).joinToString { "\n\n" }
        )
    }

    //Перемещение пользователя в списке
    fun moveUser(user: User, moveBy: Int) {
        val oldIndex = users.indexOfFirst { it.id == user.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= users.size) return
        users = ArrayList(users)
        Collections.swap(users, oldIndex, newIndex)
        notifyChanges()
    }

    fun fireUser(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index == -1) return
        val updatedUser = users[index].copy(company = "")
        users = ArrayList(users)
        users[index] = updatedUser
        notifyChanges()
    }

    fun addListener(listener: UsersListener) {
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }

    // Чтобы не повторять логику уведомления слушателей
    private fun notifyChanges() {
        listeners.forEach { it.invoke(users) }
    }

    // Случайные фото которые перемешиваем
    companion object {
        private val IMAGES = mutableListOf(
            "https://cdn.betterttv.net/emote/56e9f494fff3cc5c35e5287e/3x",
            "https://cdn.betterttv.net/emote/58ae8407ff7b7276f8e594f2/3x",
            "https://cdn.betterttv.net/emote/5590b223b344e2c42a9e28e3/3x",
            "https://cdn.betterttv.net/emote/5e0fa9d40550d42106b8a489/3x",
            "https://cdn.betterttv.net/emote/5a16ee718c22a247ead62d4a/3x"
        )
    }
}