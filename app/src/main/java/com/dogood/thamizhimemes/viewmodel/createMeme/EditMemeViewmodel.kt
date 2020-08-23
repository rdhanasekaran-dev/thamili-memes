package com.dogood.thamizhimemes.viewmodel.createMeme

import android.app.Application
import android.content.Context
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EditMemeViewmodel (application: Application):AndroidViewModel(application){
    var sharedPreferences=getApplication<Application>().getSharedPreferences("sp", Context.MODE_PRIVATE)
    var editor=sharedPreferences.edit()

    var imageString:String?=sharedPreferences.getString("editedImage","")

    val _response= MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response
}