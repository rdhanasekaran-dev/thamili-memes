package com.dogood.thamizhimemes.fragments.imageEditFragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogood.thamizhimemes.adapter.GalleryImageViewAdapter
import com.dogood.thamizhimemes.databinding.FragmentGalleryImageViewBinding
import com.dogood.thamizhimemes.utils.getGalleryImages
import com.dogood.thamizhimemes.utils.toast
import com.dogood.thamizhimemes.viewmodel.imageEdit.GalleryImageViewViewmodel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class GalleryImageViewFragment : Fragment() {
    lateinit var binding:FragmentGalleryImageViewBinding
    lateinit var recyclerView: RecyclerView
    lateinit var galleryImageViewAdapter:GalleryImageViewAdapter
    var imagesList=ArrayList<String>()
    var MY_READ_PERMISSION_CODE=101
    lateinit var imageUri: Uri
    private val viewmodel:GalleryImageViewViewmodel by lazy {
        ViewModelProviders.of(this).get(GalleryImageViewViewmodel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentGalleryImageViewBinding.inflate(inflater)
        binding.galleryImageViewmodel=viewmodel
        recyclerView=binding.galleryRecyclerView

        checkPermission()

        viewmodel.response.observe(this, Observer {
            if(it.equals("ok")){
                next()
            }
        })

        return binding.root
    }

    fun checkPermission(){
        if(ContextCompat.checkSelfPermission(context!!,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),MY_READ_PERMISSION_CODE)
        }else{
            loadImages()
        }
    }

    fun loadImages(){

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=GridLayoutManager(context!!,3)
        imagesList= getGalleryImages(context!!)

        galleryImageViewAdapter=
            GalleryImageViewAdapter(context!!,imagesList,object :GalleryImageViewAdapter.PhotoListener{
                override fun onPhotoClick(path: String) {
                    var imagePath="file:"+path
                    imageUri=Uri.parse(imagePath)
                    if(imageUri!=null) {
                        cropImage(imageUri)
                    }
                }
            })

        recyclerView.adapter=galleryImageViewAdapter
    }

    fun cropImage(imageUri:Uri){
        var i=CropImage.activity(imageUri).getIntent(context!!)
        startActivityForResult(i,CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
    }

    fun next(){
        val action = GalleryImageViewFragmentDirections.actionGalleryImageViewFragmentToWriteOnImageFragment()
        findNavController().navigate(action)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            var result=CropImage.getActivityResult(data)
            if(resultCode== RESULT_OK){
                var uri=result.uri
                if(uri!=null){
                    viewmodel.saveImageUri(uri.toString())
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==MY_READ_PERMISSION_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                loadImages()
            }
        }
    }
}