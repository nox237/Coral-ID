package com.bangkitcapstone.coral_id.ui.forum

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkitcapstone.coral_id.databinding.FragmentForumBinding

class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForumBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = activity?.getSharedPreferences("tempBangkit", Context.MODE_PRIVATE) ?: return
        Log.d("SharedPreferences",sharedPreferences.toString())
        val email = sharedPreferences.getString("emailAddress", null).toString()
        val password = sharedPreferences.getString("password", null).toString()
        Log.d("email", email)
        Log.d("password", password)
        binding?.testString?.text = "$email, $password"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}