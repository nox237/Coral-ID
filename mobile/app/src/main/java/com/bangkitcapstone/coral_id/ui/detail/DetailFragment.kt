package com.bangkitcapstone.coral_id.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.databinding.FragmentDetailBinding
import com.bangkitcapstone.coral_id.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*
import javax.inject.Inject

class DetailFragment : DaggerFragment() {
    private var  _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: DetailViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id")

        activity?.let {
            viewModel = ViewModelProvider(
                it,
                factory
            )[DetailViewModel::class.java]
            if (id != null) {
                viewModel.getCoralById(id).observe(viewLifecycleOwner, {
                    dataDisplay(it)
                })
            }
        }

        binding?.apply {
            if (activity != null)
                mtoolbarDetail.setNavigationOnClickListener {
                    Toast.makeText(activity, "Close detail fragment", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
            mappbarSheet.outlineProvider = null
        }
    }

    private fun dataDisplay(coral: CoralsEntity) {

        binding?.apply {
            Glide.with(this@DetailFragment)
                .load(coral.imagePath)
                .into(imageCoralDetail)
            val discovererAndYear = String.format(Locale.getDefault(), textCoralDiscovererAndYear.text.toString(), coral.discoverer, coral.yearDiscovered)
            textCoralFullNameDetail.text = coral.fullName
            textCoralDiscovererAndYear.text = discovererAndYear
            textCoralFamily.text = coral.coralFamily
            textCoralGenus.text = coral.coralGenus
            textCoralCharacteristic.text = coral.characteristic
            textCoralKindOfLookAlike.text = coral.kindOfLookAlike
            textCoralDistribution.text = coral.distribution
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}