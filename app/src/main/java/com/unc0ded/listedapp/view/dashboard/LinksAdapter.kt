package com.unc0ded.listedapp.view.dashboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unc0ded.listedapp.R
import com.unc0ded.listedapp.databinding.LinksItemBinding
import com.unc0ded.listedapp.model.LinkDetail
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LinksAdapter(
    private var linksList: List<LinkDetail>
) : RecyclerView.Adapter<LinksAdapter.LinksViewHolder>() {

    class LinksViewHolder(val binding: LinksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val clipboard by lazy {
            binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewHolder =
        LinksViewHolder(
            LinksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: LinksViewHolder, position: Int) {
        holder.binding.tvLinkName.text = linksList[position].title
        holder.binding.tvOutputLink.text = linksList[position].webLink
        holder.binding.tvLinkClicks.text = linksList[position].totalClicks.toString()
        holder.binding.tvLinkDate.text = linksList[position].timesAgo
        Glide.with(holder.binding.root.context)
            .load(linksList[position].originalImage)
            .placeholder(R.drawable.link)
            .into(holder.binding.sourceIv)
        holder.binding.layoutOutputLink.setOnClickListener {
            holder.clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "Link",
                    linksList[position].webLink
                )
            )
            Toast.makeText(holder.binding.root.context, "Smart link copied!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount() = linksList.size

    fun setData(links: List<LinkDetail>) {
        this.linksList = links
        notifyDataSetChanged()
    }
}