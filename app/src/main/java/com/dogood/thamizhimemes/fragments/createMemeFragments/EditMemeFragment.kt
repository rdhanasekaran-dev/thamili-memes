package com.dogood.thamizhimemes.fragments.createMemeFragments

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dogood.thamizhimemes.R
import com.dogood.thamizhimemes.databinding.FragmentEditMemeBinding
import com.dogood.thamizhimemes.viewmodel.createMeme.EditMemeViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditMemeFragment : Fragment() {
    lateinit var binding:FragmentEditMemeBinding
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var bottomSheetView: View
    private val viewmodel:EditMemeViewmodel by lazy {
        ViewModelProviders.of(this).get(EditMemeViewmodel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentEditMemeBinding.inflate(inflater)
        binding.editMemeViewmodel=viewmodel
        bottomSheetDialog= BottomSheetDialog(activity!!,R.style.BottomSheetDialogTheme)

        if(viewmodel.imageString!=""){
            var imageUri=viewmodel.imageString
            var image= Uri.parse(imageUri)
            Glide.with(context!!).load(image).into(binding.imageView)
        }

        return binding.root
    }

}