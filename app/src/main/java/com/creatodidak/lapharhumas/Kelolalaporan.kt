package com.creatodidak.lapharhumas

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_kelolalaporan.*
import org.json.JSONObject

class Kelolalaporan : AppCompatActivity() {

    lateinit var i:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelolalaporan)

        i = intent

        if(i.hasExtra("editmode")){

            if(i.getStringExtra("editmode").equals("1")){

                onEditMode()

            }

        }

        btnCreate.setOnClickListener {
            create()
        }

    }

    private fun onEditMode(){

        txLaporan.setText(i.getStringExtra("nim"))
        txPersonil.setText(i.getStringExtra("name"))
        txSatuan.setText(i.getStringExtra("address"))
        txPersonil.isEnabled = false
        txSatuan.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE
    }

    private fun create(){

        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan Laporan...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("laporan",txLaporan.text.toString())
            .addBodyParameter("personil",txPersonil.text.toString())
            .addBodyParameter("satuan",txSatuan.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("Laporan Berhasil Ditambahkan :D ")!!){
                        this@Kelolalaporan.finish()
                    }

                }

                override fun onError(anError: ANError?) {

                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.d("ONERROR", it) }
                    Toast.makeText(applicationContext,"Koneksi Gagal :(",Toast.LENGTH_SHORT).show()

                }


            })

    }

}