package com.example.datingapp.ui.screens.profiledetailscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentProfileBinding
import com.example.datingapp.databinding.FragmentProfileDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileDetailFragment : Fragment() {

    private var _binding: FragmentProfileDetailBinding? = null
    private val binding get() = _binding!!

    private var years: Int? = 33


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        _binding = FragmentProfileDetailBinding.inflate(layoutInflater, container, false)

        binding.yearsTextView.text = getString(R.string.years, years)


            return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}