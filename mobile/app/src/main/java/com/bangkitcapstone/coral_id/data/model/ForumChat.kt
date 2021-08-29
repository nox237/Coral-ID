package com.bangkitcapstone.coral_id.data.model

data class ForumChat (
    val id: Int,
    val name : String,
    val messages : String,
    val date: String
){
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass){
            return false
        }

        other as ForumChat

        if (id != other.id){
            return false
        }
        if (name != other.name){
            return false
        }
        if (messages != other.messages){
            return false
        }
        if (date != other.date){
            return false
        }

        return true
    }
}