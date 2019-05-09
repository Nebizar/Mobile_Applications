package com.example.polex.firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button2.setOnClickListener(){
            if(radioButton.isChecked()) {
                textView2.text = (editText3.text.toString().toFloat() + editText4.text.toString().toFloat()).toString()
            }
            if(radioButton2.isChecked()) {
                textView2.text = (editText3.text.toString().toFloat() - editText4.text.toString().toFloat()).toString()
            }
            if(radioButton3.isChecked()) {
                textView2.text = (editText3.text.toString().toFloat() * editText4.text.toString().toFloat()).toString()
            }
            if(radioButton4.isChecked()) {
                textView2.text = (editText3.text.toString().toFloat() / editText4.text.toString().toFloat()).toString()
            }
        }
    }


}


