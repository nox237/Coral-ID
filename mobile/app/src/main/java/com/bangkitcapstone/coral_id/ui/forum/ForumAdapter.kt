package com.bangkitcapstone.coral_id.ui.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.coral_id.data.model.ForumChat
import com.bangkitcapstone.coral_id.databinding.ItemForumBinding

class ForumAdapter(var forumChatList: ArrayList<ForumChat>) : RecyclerView.Adapter<ForumAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemForumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ForumChat){
            binding.apply {
                binding.tvUsername.text = data.name
                binding.tvMessage.text = data.messages
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

    fun submitlist(newforumChatList:ArrayList<ForumChat>){
        val oldList = forumChatList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ForumChatDiffCallback(
                oldList,
                newforumChatList
            )
        )
        forumChatList = newforumChatList
        diffResult.dispatchUpdatesTo(this)
    }

    class ForumChatDiffCallback(
        var oldForumChat: ArrayList<ForumChat>,
        var newForumChat: ArrayList<ForumChat>
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldForumChat.size
        }

        override fun getNewListSize(): Int {
            return newForumChat.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldForumChat.get(oldItemPosition).id == newForumChat.get(newItemPosition).id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldForumChat.get(oldItemPosition).equals(newForumChat.get(newItemPosition))
        }

    }
}