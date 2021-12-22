package com.creatodidak.lapharhumas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.laporanlist.view.*

class Laporanadapter (private val context: Context,
                      private val arrayList: ArrayList<Laporanmodel>)
                        : RecyclerView.Adapter<Laporanadapter.Holder>()
                        {class Holder(val view: View) : RecyclerView.ViewHolder(view)

                            override fun onCreateViewHolder(
                                parent: ViewGroup,
                                viewType: Int
                            ): Holder {
                                return Holder(LayoutInflater.from(parent.context).inflate(R.layout.laporanlist,parent,false))
                            }

                            override fun onBindViewHolder(holder: Holder, position: Int) {
                                holder.view.laporan.text = arrayList?.get(position)?.laporan
                                holder.view.user.text = arrayList?.get(position)?.personil
                                holder.view.satuan.text = arrayList?.get(position)?.satuan
                                holder.view.waktu.text = arrayList?.get(position)?.time
                            }

                            override fun getItemCount(): Int = arrayList!!.size
                        }