package com.dogood.thamizhimemes.fragments.imageEditFragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dogood.thamizhimemes.canvas.MyCanvas
import com.dogood.thamizhimemes.databinding.FragmentWriteOnImageBinding
import com.dogood.thamizhimemes.utils.toast
import com.dogood.thamizhimemes.viewmodel.imageEdit.WriteOnImageViewmodel
import java.io.ByteArrayOutputStream

class WriteOnImageFragment : Fragment() {
    lateinit var binding: FragmentWriteOnImageBinding
    private val viewmodel:WriteOnImageViewmodel by lazy {
        ViewModelProviders.of(this).get(WriteOnImageViewmodel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentWriteOnImageBinding.inflate(inflater)
        binding.writeOnImageViewmodel=viewmodel

        var imageUri=viewmodel.imageString

        val canvasView=MyCanvas(context!!)

        binding.drawView.addView(canvasView)

        if(imageUri!=""){
            var image= Uri.parse(imageUri)
            Glide.with(context!!).load(image).into(binding.croppedImage)
        }

        binding.undo.setOnClickListener {
            canvasView.clickUndo()
        }

        binding.redo.setOnClickListener {
            canvasView.clickRedo()
        }

        viewmodel.response.observe(this, Observer {
            if(it.equals("ok")){
                next()
            }
        })

        binding.next.setOnClickListener {
            updateImage()
        }

        return binding.root
    }

    fun bitmapFromView(view: View):Bitmap{

        var bitmap=Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        var canvas=Canvas(bitmap)
        var drawable=view.background

        if(drawable!=null){
            drawable.draw(canvas)
        }else{
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)

        return  bitmap
    }

    fun bitmapToString(bitmap:Bitmap):String{
        var baos=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        var b=baos.toByteArray()
        var imageString=Base64.encodeToString(b,Base64.DEFAULT)
        return imageString
    }

    fun updateImage(){
        var bitmap=bitmapFromView(binding.imageEditLayout)
        var imageString=bitmapToString(bitmap)
        viewmodel.updateImage(imageString)
    }

    fun next(){
        val action=WriteOnImageFragmentDirections.actionWriteOnImageFragmentToTextViewOnImageFragment()
        findNavController().navigate(action)
    }
}