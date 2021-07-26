package com.bangkitcapstone.coral_id.ui.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private val context = activity
    private val sharedPreferences = context?.getSharedPreferences("tempBangkit", Context.MODE_PRIVATE)
    val editor = sharedPreferences?.edit()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.loginLink?.setOnClickListener {
            activity?.onBackPressed()
        }

        binding?.btnRegister?.setOnClickListener {
            val email = binding?.email?.text.toString()
            val password = binding?.password?.text.toString()
            val confirm_password = binding?.confirmPassword?.text.toString()

            if (password == confirm_password){
                editor?.putString("emailAddress", email)
                editor?.putString("password", password)
                editor?.apply()

                it.findNavController().navigate(R.id.action_registerFragment_to_forumFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}