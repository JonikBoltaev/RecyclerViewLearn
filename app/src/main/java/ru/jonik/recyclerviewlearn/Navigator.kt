package ru.jonik.recyclerviewlearn

import ru.jonik.recyclerviewlearn.model.User

interface Navigator {
    fun showDetails(user: User)

    fun goBack()

    fun toast(messageRes: Int)
}