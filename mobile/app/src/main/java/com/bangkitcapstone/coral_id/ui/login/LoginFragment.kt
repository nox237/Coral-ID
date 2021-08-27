package com.bangkitcapstone.coral_id.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bangkitcapstone.coral_id.BuildConfig.BASE_URL
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentLoginBinding
import com.bangkitcapstone.coral_id.ui.forum.ForumFragment
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private val context = activity
    private val sharedPreferences = context?.getSharedPreferences("tempBangkit",Context.MODE_PRIVATE)
    private val editor = sharedPreferences?.edit()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.registerLink?.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding?.btnLogin?.setOnClickListener{
            val email = binding?.email?.text.toString()
            val password = binding?.password?.text.toString()

//            editor?.putString("emailAddress", email)
//            editor?.putString("password", password)
//            editor?.apply()
            val client = AsyncHttpClient()
            val requestParams = RequestParams()
            requestParams.add("email", email)
            requestParams.add("password", password)
            client.post(BASE_URL + "login", requestParams, object : AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    val response = String(responseBody)
                    Log.d("message", response)
                    it.findNavController().navigate(R.id.action_loginFragment_to_forumFragment)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    Log.d("error", error.toString())
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}