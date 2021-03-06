package ru.jonik.recyclerviewlearn.model

// Содержит поля для отображения 1 элемента списка
data class User(
    val id: Long,
    val photo: String,
    val name: String,
    val company: String
)

data class UserDetails(
    val user: User,
    val details: String
)
