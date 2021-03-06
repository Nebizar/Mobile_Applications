package com.example.polex.findthenumber

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var init
        var number = 1//(1..20).shuffled().first()
        var counter = 1
        var fullScore = 0

        //Toaster
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, "Started a NEW GAME", duration)

        //Dialog
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("The End!")
        dialog.setOnCancelListener { toast.show() }

        //Internal Memory File
        val fileName = "BS.txt"
        val file = File(filesDir, fileName)
        //file.createNewFile()

        //Read best score from Internal Memory
        var user = Users()

        var input = file.bufferedReader().readLines()
        var data = input[0].split(",")
        user.id = data[0].toInt()
        user.username = data[1]
        user.password = data[2]
        user.score = data[3].toInt()
        textView2.text = ("Current score: " + user.score.toString())


        //check button
        button.setOnClickListener {
            if (counter > 10){
                dialog.setMessage("You lose ! You needed more than 10 tries to guess the number !")
                dialog.show()
                number = (0..20).shuffled().first()
                counter = 0
            }
            else if(editText.text.toString().toInt() == number) {
                dialog.setMessage("Congratulations ! You needed " + counter.toString() + " tries to guess the number")
                dialog.show()

                number = (0..20).shuffled().first()


                user.score += calculatePoints(counter)

                val fileOutputStream: FileOutputStream
                try {
                    fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
                    fileOutputStream.write((user.id.toString()+",").toByteArray())
                    fileOutputStream.write((user.username+",").toByteArray())
                    fileOutputStream.write((user.password+",").toByteArray())
                    fileOutputStream.write((user.score.toString()+",").toByteArray())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val check = EndpointConnection.SendNameMessage(this).execute(user.username, user.score.toString())

                textView2.text = "Your Score: " + user.score.toString()

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

        //best scores button
        button3.setOnClickListener{
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }

        button7.setOnClickListener{
            dbHandler = DatabaseHandler(this)
            dbHandler!!.updateUser(user)
            file.delete()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun calculatePoints(moves: Int):Int{
        if (moves == 1) return 5
        if (moves in 2..4) return 3
        if (moves in 5..6) return 2
        if (moves in 7..10) return 1
        return -1
    }
}

