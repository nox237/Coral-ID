package com.bangkitcapstone.coral_id.ui.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.data.model.ForumChat
import com.bangkitcapstone.coral_id.databinding.ItemForumBinding

class ForumAdapter(var forumChatList: ArrayList<ForumChat>) : RecyclerView.Adapter<ForumAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemForumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ForumChat){
            binding.apply {
                binding.tvUsername.text = data.username
                binding.tvMessage.text = data.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemForumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forumChatList[position])
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return forumChatList.size
    }

}