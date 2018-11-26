package com.example.jack.eyepay

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File
import android.widget.Toast
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    val TESS_DATA = "/tessdata"
    lateinit var textView: TextView
    lateinit var tessBaseApi: TessBaseAPI
    lateinit var outputFileDir: Uri
    val DATA_PATH = Environment.getExternalStorageDirectory().toString()+"/Tess"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        var btn = findViewById(R.id.button) as Button
        btn.setOnClickListener {
            startCameraActivity()
        }
    }

    fun startCameraActivity(){
        try {
            var imagePath = DATA_PATH + "/imgs"
            var dir = File(imagePath)
            if(!dir.exists()){
                dir.mkdir()
            }
            var imageFilePath = imagePath + "/ocr.jpg"
            outputFileDir = Uri.fromFile(File(imageFilePath))
            var pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileDir)
            if(pictureIntent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(pictureIntent, 100)
            }

        }catch (e: Exception){
            Log.e(TAG, e.message)
        }
    }

    internal fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent){
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            prepareTessData()
            startOCR(outputFileDir)
        }
        else{
            Toast.makeText(applicationContext, "image problem", Toast.LENGTH_SHORT).show()
        }
    }

    private fun prepareTessData(){
        try {
            var dir = File(DATA_PATH+TESS_DATA)
            if(!dir.exists()){
                dir.mkdir()
            }
            var fileList = arrayListOf<String>()
            for(filename in fileList){
                var pathToDataFile = DATA_PATH + TESS_DATA + "/" + filename
                if(!(File(pathToDataFile).exists())){
                    var inside = assets.open(filename)
                    var outside = FileOutputStream(pathToDataFile)
                    var buff = byteArrayOf()
                    var len: Int
                    while (inside.read(buff) > 0){
                        len = inside.read(buff)
                        outside.write(buff, 0, len)
                    }
                    inside.close()
                    outside.close()
                }
            }
        }catch (e: Exception){
            Log.e(TAG, e.message)
        }
    }

    private fun startOCR(imageUri:Uri){
        try {
            var options = BitmapFactory.Options()
            options.inSampleSize = 7
            var bitmap = BitmapFactory.decodeFile(imageUri.path, options)
            var result = getText(bitmap)
            textView.setText(result)
        }catch (e: Exception){
            Log.e(TAG, e.message)
        }
    }

    private fun getText(bitmap: Bitmap): String {
        try {
            var tessBaseApi = TessBaseAPI()
        }catch (e: Exception){
            Log.e(TAG, e.message)
        }
        tessBaseApi.init(DATA_PATH, "spa")
        tessBaseApi.setImage(bitmap)
        var retString = ""
        try {
            retString = tessBaseApi.utF8Text
        }catch (e: Exception){
            Log.e(TAG, e.message)
        }
        tessBaseApi.end()
        return retString
    }
}
