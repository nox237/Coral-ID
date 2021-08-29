package com.bangkitcapstone.coral_id.ui.forum

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.data.model.ForumChat
import com.bangkitcapstone.coral_id.databinding.FragmentForumBinding
import com.google.gson.GsonBuilder
import com.loopj.android.http.*
import cz.msebera.android.httpclient.Header

class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding
    private var forumChatList = ArrayList<ForumChat>()
    private lateinit var pref: SharedPreferences
    private lateinit var forumAdapter: ForumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForumBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("tempBangkit", Context.MODE_PRIVATE) ?: return
        forumAdapter = ForumAdapter(forumChatList)

        val refresh_token = pref.getString("refresh_token", null).toString()
        val access_token = pref.getString("access_token", null).toString()

        Log.d("refresh_token",refresh_token)
        Log.d("access_token", access_token)
        refresh_messages(access_token)
        setRecyclerView()

        binding?.button2?.setOnClickListener {
            refresh_messages(access_token)
        }

        binding?.button?.setOnClickListener {
            pref.edit().clear().commit()
            pref.edit().apply()
            Toast.makeText(getContext(),"Logout Account Succesfully", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }

        binding?.aCBtnSend?.setOnClickListener {
            val message = binding?.aCEtxtMessage?.text.toString()

            val client = AsyncHttpClient()
            val requestParams = RequestParams()

            client.addHeader("Authorization", "Bearer " + access_token);
            requestParams.add("messages", message)

            client.post("http://b4d4-180-214-232-15.ngrok.io/api/forum/", requestParams, object : AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    Toast.makeText(getContext(),"Upload new message to Forum", Toast.LENGTH_SHORT).show()
                    refresh_messages(access_token)
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
        }
    }

    private fun refresh_messages(access_token: String) {
        val client = AsyncHttpClient()
        val requestParams = RequestParams()

        client.addHeader("Authorization", "Bearer " + access_token)

        client.get("http://b4d4-180-214-232-15.ngrok.io/api/forum/", requestParams, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                Log.d("response",String(responseBody))
                val gson = GsonBuilder().create()

                val message_list : Array<ForumChat> = gson.fromJson(String(responseBody), Array<ForumChat>::class.java)
                var temp_arraylist_forumchat = ArrayList<ForumChat>()
                message_list.forEachIndexed  { idx, tut -> temp_arraylist_forumchat.add(tut) }

                forumAdapter.submitlist(temp_arraylist_forumchat)

                Toast.makeText(getContext(),"Updated Messages", Toast.LENGTH_SHORT).show()
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
    }

    private fun setRecyclerView() {
        binding?.aCRViewMessages?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = forumAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}