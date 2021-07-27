package com.example.listview_with_json_in_kotlin

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Adapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONStringer
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    val url = "https://jsonplaceholder.typicode.com/comments"
    lateinit var prog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AsyncTaskMethod().execute(url)
    }

    inner class AsyncTaskMethod : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            val prog = ProgressDialog(this@MainActivity)
            prog.setMessage("Plase wait few minutes")
            prog.setCancelable(false)
            Handler().postDelayed({ prog.dismiss() }, 3000)
            prog.show()
        }

        override fun doInBackground(vararg url: String?): String {
            val res: String
            val connection = URL(url[0]).openConnection() as HttpsURLConnection
            try {
                connection.connect()
                res = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return res
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {

                jsonResualt(result)

            }
        }

        fun jsonResualt(jsonSring: String) {
            val jsonArray = JSONArray(jsonSring)
            val list = ArrayList<ModelClass>()
            var i = 0
            while (i < jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                list.add(
                    ModelClass(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("email"),
                        jsonObject.getString("body")
                    )
                )
                i++
            }
            var adapter = MyAdapter(this@MainActivity,list)
            list_item.adapter =adapter
        }
    }
}