package ru.jonik.recyclerviewlearn

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.jonik.recyclerviewlearn.databinding.ItemUserBinding
import ru.jonik.recyclerviewlearn.model.User

interface UserActionListener {
    fun onUserMove(user: User, moveBy: Int)
    fun onUserDelete(user: User)
    fun onUserDetails(user: User)
}

// Адаптер - чтобы преобразовать данные в элементы которые будут созданы на основе макетного файла
class UsersAdapter(private val actionListener: UserActionListener) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    // Список пользователей, Сеттер нужен для уведомлении об обновлении при новом значении
    var users: List<User> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.iv_more -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    private fun showPopupMenu(view: View) {

        val popupMenu = PopupMenu(view.context, view)
        val user = view.tag as User
        val position = users.indexOfFirst { it.id == user.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, "Move Up").apply { isEnabled = position > 0 }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, "Move Down")
            .apply { isEnabled = position < users.size - 1 }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onUserMove(user, -1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserMove(user, 1)
                }
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    // Адаптер должен знать количество элементов в списке
    override fun getItemCount(): Int = users.size

    // Когда Recycler View хочет создать новый элемент списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.ivMore.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    // Для обновления элемента списка
    // Ньюансы с if else
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user: User = users[position]
        with(holder.binding) {
            holder.itemView.tag = user
            ivMore.tag = user
            tvUserName.text = user.name
            tvUserCompany.text = user.company
            if (user.photo.isNotBlank()) {
                Glide.with(ivAvatar.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(ivAvatar)
            } else {
                ivAvatar.setImageResource(R.drawable.ic_person)
            }
        }
    }

    class UsersViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}