package com.dogood.thamizhimemes.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

fun getGalleryImages(context: Context):ArrayList<String>{
    var uri:Uri
    var cursor:Cursor
    var column_index_data:Int
    var column_index_folder_name:Int
    var listOfAllGalleryImages= arrayListOf<String>()
    var absolutePathOfImage:String

    uri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    var projection= arrayOf(MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    var orderBy=MediaStore.Video.Media.DATE_TAKEN

    cursor=context.contentResolver.query(uri,projection,null,null,orderBy+" DESC")!!

    column_index_data=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

    //get folder name
    //column_index_folder_name=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

    while (cursor.moveToNext()){
        absolutePathOfImage=cursor.getString(column_index_data)
        listOfAllGalleryImages.add(absolutePathOfImage)
    }

    return listOfAllGalleryImages
}