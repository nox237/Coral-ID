package com.bangkitcapstone.coral_id.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkitcapstone.coral_id.R
import kotlinx.android.synthetic.main.info_dialog_fragment.*

class InfoDialog : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_dialog_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView2.setOnClickListener(){
            activity?.onBackPressed()
        }
    }
}