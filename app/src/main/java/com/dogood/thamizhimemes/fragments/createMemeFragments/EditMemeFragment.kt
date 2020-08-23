package com.dogood.thamizhimemes.fragments.createMemeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.dogood.thamizhimemes.R
import com.dogood.thamizhimemes.databinding.FragmentEditMemeBinding
import com.dogood.thamizhimemes.viewmodel.createMeme.EditMemeViewmodel

class EditMemeFragment : Fragment() {
    lateinit var binding:FragmentEditMemeBinding
    private val viewmodel:EditMemeViewmodel by lazy {
        ViewModelProviders.of(this).get(EditMemeViewmodel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentEditMemeBinding.inflate(inflater)
        binding.editMemeViewmodel=viewmodel

        return binding.root
    }
}