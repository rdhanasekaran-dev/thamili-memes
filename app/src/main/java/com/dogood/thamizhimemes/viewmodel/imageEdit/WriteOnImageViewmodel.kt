package com.dogood.thamizhimemes.viewmodel.imageEdit

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WriteOnImageViewmodel (application: Application):AndroidViewModel(application){
    var sharedPreferences=getApplication<Application>().getSharedPreferences("sp", Context.MODE_PRIVATE)
    var editor=sharedPreferences.edit()

    var imageString:String?=sharedPreferences.getString("editedImage","")

    val _response= MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    fun updateImage(imagestring: String){
        imageString=imagestring
        if(imageString!=null){
            editor.putString("editedImage",imageString)
            editor.commit()
            editor.apply()
            _response.value="ok"
        }
    }

}