package com.example.polex.findthenumber

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.io.FileOutputStream

class LoginActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fileName = "BS.txt"
        val file = File(filesDir, fileName)
        if(!file.exists()){
            file.createNewFile()
        }

        //init db
        dbHandler = DatabaseHandler(this)

        button5.setOnClickListener(){
            if(validation()) {
                var username = editText2.text.toString()
                var password = editText3.text.toString()
                var user = dbHandler!!.validateUser(username, password)
                if(!user.username.equals("")){
                    //save looged user in cache
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
                    val thread = Thread() {
                        run {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    thread.start()
                }
                else{
                    val toast = Toast.makeText(this,"Wrong Username or Password", Toast.LENGTH_LONG).show()
                }
            }
        }

        button6.setOnClickListener(){
            var user: Users = Users()
            if(validation()) {
                var success: Boolean = false
                user.id = 1//dbHandler!!.getMaxID() + 1
                user.username = editText2.text.toString()
                user.password = editText3.text.toString()
                success = dbHandler!!.addUser(user)

                if (success){
                    val toast = Toast.makeText(this,"Saved Successfully", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun validation(): Boolean{
        var validate = false

        if (!editText2.text.toString().equals("") && !editText3.text.toString().equals("")){
            validate = true
        }else{
            validate = false
            val toast = Toast.makeText(this,"Fill all details", Toast.LENGTH_LONG).show()
        }

        return validate
    }
}
