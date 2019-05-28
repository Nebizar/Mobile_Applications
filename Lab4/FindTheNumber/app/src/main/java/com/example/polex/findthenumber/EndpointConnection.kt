package com.example.polex.findthenumber

import android.os.AsyncTask
import android.view.View
import kotlinx.android.synthetic.main.activity_message.*
import java.net.HttpURLConnection
import java.net.URL

class EndpointConnection {

    class GetNameMessage( private var activity: MessageActivity?): AsyncTask<String, String, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            activity?.progressBar?.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String {
            var result: String
            val url = "http://hufiecgniezno.pl/br/record.php?f=get"
            val connection =  URL(url).openConnection() as HttpURLConnection
            try {
                connection.connect()
                result = connection.inputStream.use{it.reader().use{reader -> reader.readText()} }
            } finally {
                connection.disconnect()
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            activity?.progressBar?.visibility = View.INVISIBLE
            activity?.textView3?.text = result
        }
    }

    class SendNameMessage(private var activity: MainActivity?): AsyncTask<String,String,String>(){
        override fun doInBackground(vararg params: String?): String {
            var result: String
            val url = "http://hufiecgniezno.pl/br/record.php?f=add&id="+params[0]+"&r="+params[1]
            val connection =  URL(url).openConnection() as HttpURLConnection
            try {
                connection.connect()
                result = connection.inputStream.use{it.reader().use{reader -> reader.readText()} }
            } finally {
                connection.disconnect()
            }
            return result
        }
    }
}