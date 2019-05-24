package com.example.newton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.R.string
import android.support.v4.app.FragmentActivity
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.google.gson.TypeAdapter
import com.google.gson.Gson



class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newton.now.sh")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newton = retrofit.create(NewtonAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun buttonHandle(view: View){

        if(view.tag.toString() == "zeroes"){
            val call = newton.getZeroes(findViewById<TextView>(R.id.expression).text.toString())
            call.enqueue(object: Callback<NewtonDTO2>{
                override fun onFailure(call: Call<NewtonDTO2>, t: Throwable){
                    findViewById<TextView>(R.id.result).text = call.toString()
                    Log.d("response1a","ResponseValue: failure")

                }

                override fun onResponse(call: Call<NewtonDTO2>,response: Response<NewtonDTO2>){
                    val body = response.body()
                    body?.let{
                        Log.d("response1b","ResponseValue: ${body.result}")
                        if(body.result != null)//bug? {
                        {
                            findViewById<TextView>(R.id.result).text = body.result.toString()
                        }
                        else{
                            findViewById<TextView>(R.id.result).text = resources.getText(R.string.wrong)
                        }
                    } ?: run {
                        Log.d("response1c","ResponseValue: ${resources.getText(R.string.wrong)}")
                        findViewById<TextView>(R.id.result).text = resources.getText(R.string.wrong)
                    }

                }
            })
        }else{
            val call = newton.getResult(view.tag.toString(), findViewById<TextView>(R.id.expression).text.toString())

            call.enqueue(object: Callback<NewtonDTO>{
                override fun onFailure(call: Call<NewtonDTO>, t: Throwable){
                    findViewById<TextView>(R.id.result).text = call.toString()
                    Log.d("response2a","ResponseValue: failure")

                }

                override fun onResponse(call: Call<NewtonDTO>,response: Response<NewtonDTO>){
                    val body = response.body()
                    body?.let{
                        Log.d("response2b","ResponseValue: ${body.result}")
                        if(body.result != null)//bug?
                        {
                            findViewById<TextView>(R.id.result).text = body.result
                        } else{
                            findViewById<TextView>(R.id.result).text = resources.getText(R.string.wrong)
                        }
                    } ?: run {
                        Log.d("response2c","ResponseValue: ${resources.getText(R.string.wrong)}")
                        findViewById<TextView>(R.id.result).text = resources.getText(R.string.wrong)
                    }

                }
            })
        }
    }
}
