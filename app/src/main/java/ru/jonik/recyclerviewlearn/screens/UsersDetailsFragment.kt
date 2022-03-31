package ru.jonik.recyclerviewlearn.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import ru.jonik.recyclerviewlearn.R
import ru.jonik.recyclerviewlearn.databinding.FragmentUserDetailsBinding

class UsersDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel: UserDetailsViewModel by viewModels { factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser(requireArguments().getLong(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)

        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            binding.tvUserName.text = it.user.name
            if (it.user.photo.isNotBlank()) {
                Glide.with(this)
                    .load(it.user.photo)
                    .circleCrop()
                    .into(binding.ivAvatar)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_person)
                    .into(binding.ivAvatar)
            }
            binding.tvPersonDescription.text = it.details
        })

        binding.btnDelete.setOnClickListener {
            viewModel.deleteUser()
            navigator().goBack()
        }
        return binding.root
    }

    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long): UsersDetailsFragment {
            val fragment = UsersDetailsFragment()
            fragment.arguments = bundleOf(ARG_USER_ID to userId)
            return fragment
        }
    }

}