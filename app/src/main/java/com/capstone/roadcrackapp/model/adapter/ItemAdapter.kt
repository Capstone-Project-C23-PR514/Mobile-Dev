package com.capstone.roadcrackapp.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.roadcrackapp.databinding.ItemViewBinding
import com.capstone.roadcrackapp.model.response.ReportsItem

class ItemAdapter(
    private val ListReport: List<ReportsItem>, val context: Context,
    private val listener: ReportListener
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val binding = ItemViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val report = ListReport[position]
        holder.binding.apply {
            tvLocation.text = report.lokasi
            tvDesc.text = report.desc
            tvDate.text = report.createdAt

            Glide.with(context)
                .load(report.gambar)
                .into(ivPhoto)
        }
        holder.itemView.setOnClickListener {
            listener.onItemClicker(report,holder.binding.ivPhoto,holder.binding.tvLocation,holder.binding.tvDesc,holder.binding.tvDate)
        }
    }

    override fun getItemCount() = ListReport.size

    class ViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    interface ReportListener {
        fun onItemClicker(report: ReportsItem, ivPhoto: ImageView, tvLocation: TextView, tvDesc: TextView, tvDate: TextView)
    }


}