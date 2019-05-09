package com.example.polex.findthenumber

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var init
        var number = (1..20).shuffled().first()
        var counter = 1
        var bestScore = 9999999

        //Toaster
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, "Started a NEW GAME", duration)

        //Dialog
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("You win!")
        dialog.setOnCancelListener { toast.show() }

        //Internal Memory File
        val fileName = "BS.txt"
        val file = File(filesDir, fileName)
        file.createNewFile()

        //Read best score from Internal Memory
        var fileInputStream: FileInputStream? = null
        fileInputStream = openFileInput(fileName)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }

        if(stringBuilder.isNotEmpty()) {
            bestScore = stringBuilder.toString().toInt()
            textView2.text = ("Best Score: " + stringBuilder.toString() + " moves").toString()
        }
        else {
            textView2.text = ("No scores yet - be first to play!").toString()
        }

        //check button
        button.setOnClickListener {
            if(editText.text.toString().toInt() == number) {
                dialog.setMessage("Congratulations ! You needed " + counter.toString() + " tries to guess the number")
                dialog.show()

                number = (0..20).shuffled().first()

                if(bestScore > counter) {
                    bestScore = counter

                    val fileOutputStream: FileOutputStream
                    try {
                        fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
                        fileOutputStream.write(bestScore.toString().toByteArray())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    textView2.text = "Best Score: " + bestScore.toString() + " moves"
                }
                counter = 0
            }
            else {
                counter++
            }
        }

        //new game button
        button2.setOnClickListener{
            number = (0..20).shuffled().first()
            counter = 0
            toast.show()
        }


    }
}

