package com.dogood.thamizhimemes.fragments.imageEditFragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dogood.thamizhimemes.R
import com.dogood.thamizhimemes.databinding.FragmentTextViewOnImageBinding
import com.dogood.thamizhimemes.viewmodel.imageEdit.TextViewOnImageViewmodel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.textfield_bottom_sheet.*
import java.lang.Exception
import java.util.*

class TextViewOnImageFragment : Fragment(){
    lateinit var binding: FragmentTextViewOnImageBinding
    lateinit var imageBitmap: Bitmap
    lateinit var textView: TextView
    var dx=0
    var dy=0
    var x=0
    var y=0
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var bottomSheetView:View
    lateinit var imm:InputMethodManager

    private val viewmodel:TextViewOnImageViewmodel by lazy {
        ViewModelProviders.of(this).get(TextViewOnImageViewmodel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentTextViewOnImageBinding.inflate(inflater)
        binding.textViewOnImageViewmodel=viewmodel

        var imageString=viewmodel.imageString

        imageBitmap=stringtoBitmap(imageString!!)!!

        if(imageBitmap!=null) {
            if (viewmodel.imageString != null) {
                Glide.with(context!!).load(imageBitmap).into(binding.imageView)
            }
        }

        showTextField()

        binding.add.setOnClickListener {
            showTextField()

        }



        return binding.root
    }

    fun stringtoBitmap(imageString: String):Bitmap?{
        try{
            var b=Base64.decode(imageString,Base64.DEFAULT)
            var bitmap=BitmapFactory.decodeByteArray(b,0,b.size)
            return bitmap
        }catch (e:Exception){
            return null
        }
    }

    fun showTextField(){
        bottomSheetDialog= BottomSheetDialog(activity!!,R.style.BottomSheetDialogTheme)
        bottomSheetView=LayoutInflater.from(context).inflate(R.layout.textfield_bottom_sheet,bottomSheetContainer,false)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetView.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
        bottomSheetDialog.show()
        bottomSheetDialog.imageText.postDelayed(Runnable {
            imm=activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(bottomSheetDialog.imageText,InputMethodManager.SHOW_FORCED)
        },100)
        bottomSheetDialog.imageText.requestFocus()
        bottomSheetDialog.imageText.isFocusableInTouchMode=true
        bottomSheetDialog.done.setOnClickListener {
            var text=bottomSheetDialog.imageText.text.toString()
            if(!text.isNullOrEmpty()){
                imm.hideSoftInputFromWindow(bottomSheetDialog.imageText.windowToken,0)
                bottomSheetDialog.dismiss()
                createTextView(text)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createTextView(text:String){
        textView=TextView(context)
        textView.text=text
        textView.setPadding(10,0,10,0)
        textView.textSize=20F
        textView.setTypeface(Typeface.DEFAULT_BOLD)
        textView.setBackgroundColor(Color.WHITE)
        textView.setTextColor(Color.BLACK)
        textView.setOnTouchListener(onTouchListener())
        binding.rl.addView(textView)
    }

    fun onTouchListener():View.OnTouchListener{
        return object :View.OnTouchListener{

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                 x=event!!.rawX.toInt()
                 y=event!!.rawY.toInt()
                when(event!!.action){
                    MotionEvent.ACTION_DOWN->{
                        var layoutParams=v!!.layoutParams as RelativeLayout.LayoutParams
                        dx=x-layoutParams.leftMargin
                        dy=y-layoutParams.topMargin
                    }
                    MotionEvent.ACTION_UP->{
                    }
                    MotionEvent.ACTION_MOVE->{
                        var layoutParams=v!!.layoutParams as RelativeLayout.LayoutParams
                        layoutParams.leftMargin=(x-dx)
                        layoutParams.topMargin=(y-dy)
                        layoutParams.rightMargin=0
                        layoutParams.bottomMargin=0
                        v!!.layoutParams=layoutParams
                    }
                }
                textView.invalidate()
                return true
            }

        }
    }

}