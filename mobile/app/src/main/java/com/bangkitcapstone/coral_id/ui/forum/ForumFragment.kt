package com.bangkitcapstone.coral_id.ui.forum

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.data.model.ForumChat
import com.bangkitcapstone.coral_id.databinding.FragmentForumBinding
import com.bangkitcapstone.coral_id.ui.result.ResultAdapter
import com.loopj.android.http.*
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding
    private var forumChatList = ArrayList<ForumChat>()
    private lateinit var pref: SharedPreferences

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

        pref = activity?.getSharedPreferences("tempBangkit", Context.MODE_PRIVATE) ?: return

        val refresh_token = pref.getString("refresh_token", null).toString()
        val access_token = pref.getString("access_token", null).toString()

        Log.d("refresh_token",refresh_token)
        Log.d("access_token", access_token)
        generateData()
        setRecyclerView()

        val client = AsyncHttpClient()
        val requestParams = RequestParams()
        client.get("https://api.sampleapis.com/avatar/info", requestParams, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val response = String(responseBody)
                Log.d("message", response)
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

    private fun setRecyclerView() {
        binding?.aCRViewMessages?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForumAdapter(forumChatList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun generateData(){
        forumChatList.add(ForumChat("Jimmy", "testing"))
        forumChatList.add(ForumChat("Jimmy", "testing123"))
        forumChatList.add(ForumChat("Jimmy", "testingTester"))
    }
}