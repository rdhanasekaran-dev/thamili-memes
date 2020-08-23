package com.dogood.thamizhimemes.viewmodel.imageEdit

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GalleryImageViewViewmodel (application: Application):AndroidViewModel(application){
    var sharedPreferences=getApplication<Application>().getSharedPreferences("sp", Context.MODE_PRIVATE)
    var editor=sharedPreferences.edit()

    val _response=MutableLiveData<String>()
    val response:LiveData<String>
        get() = _response

    var croppedImageUri:String?=null

    fun saveImageUri(uri:String){
        croppedImageUri=uri
        editor.putString("editedImage",croppedImageUri)
        editor.commit()
        editor.apply()
        if(croppedImageUri!=null){
            _response.value="ok"
        }
    }
}