package com.creatodidak.lapharhumas

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var arrayList = ArrayList<Laporanmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Data Laporan"

        allLaporan.setHasFixedSize(true)
        allLaporan.layoutManager = LinearLayoutManager(this)

        mFloatingActionButton.setOnClickListener{
            startActivity(Intent(this, Kelolalaporan::class.java))
        }

    }


    override fun onResume() {
        super.onResume()
        loadLaporan()}


    private fun loadLaporan(){

        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{

                override fun onResponse(response: JSONObject?) {

                    arrayList.clear()

                    val jsonArray = response?.optJSONArray("result")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()
                    }

                    for(i in 0 until jsonArray?.length()!!){

                        val jsonObject = jsonArray?.optJSONObject(i)
                        arrayList.add(
                            Laporanmodel(jsonObject.getString("laporan"),
                                         jsonObject.getString("personil"),
                                         jsonObject.getString("satuan"),
                                         jsonObject.getString("time"),
                            )
                        )

                        if(jsonArray?.length() - 1 == i){

                            loading.dismiss()
                            val adapter = Laporanadapter(applicationContext,arrayList)
                            adapter.notifyDataSetChanged()
                            allLaporan.adapter = adapter

                        }

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.d("ONERROR", it) }
                    Toast.makeText(applicationContext,"Koneksi Terputus :(",Toast.LENGTH_SHORT).show()
                }
            })


    }

}