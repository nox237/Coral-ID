package com.bangkitcapstone.coral_id.ui.forum

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.coral_id.databinding.FragmentForumBinding
import com.bangkitcapstone.coral_id.ui.result.ResultAdapter
import com.loopj.android.http.*
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

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

//        val email = sharedPreferences.getString("emailAddress", null).toString()
//        val password = sharedPreferences.getString("password", null).toString()
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
            adapter = ForumAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}