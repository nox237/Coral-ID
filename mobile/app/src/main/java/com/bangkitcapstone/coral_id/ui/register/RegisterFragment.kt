package com.bangkitcapstone.coral_id.ui.register

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkitcapstone.coral_id.BuildConfig
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentRegisterBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

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
            val name = binding?.name?.text.toString()
            val email = binding?.email?.text.toString()
            val password = binding?.password?.text.toString()
            val confirm_password = binding?.confirmPassword?.text.toString()

            if (password == confirm_password){
                editor?.putString("name", name)
                editor?.putString("emailAddress", email)
                editor?.putString("password", password)
                editor?.apply()

                val client = AsyncHttpClient()
                val requestParams = RequestParams()

                requestParams.add("name", name)
                requestParams.add("email", email)
                requestParams.add("password", password)

                client.post("https://86c0-116-206-12-59.ngrok.io/api/auth/register/", requestParams, object : AsyncHttpResponseHandler(){
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        responseBody: ByteArray
                    ) {
                        val response = String(responseBody)
                        Log.d("message", response)

                        val response_data = JSONObject(response)
                        val refresh_token = response_data.getString("refresh_token").toString()
                        val access_token = response_data.getString("access_token").toString()
                        val message = response_data.getString("message").toString()

                        Log.d("message_from_response", message)
                        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        responseBody: ByteArray?,
                        error: Throwable?
                    ) {
                        Log.d("error", error.toString())
                        Toast.makeText(getContext(), "Error:" + error.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
//                it.findNavController().navigate(R.id.action_registerFragment_to_forumFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}