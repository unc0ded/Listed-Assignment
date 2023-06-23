package com.unc0ded.listedapp.view.dashboard

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unc0ded.listedapp.R
import com.unc0ded.listedapp.databinding.StatListItemBinding
import com.unc0ded.listedapp.model.Stat
import com.unc0ded.listedapp.model.StatType
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class StatsAdapter(
    private var statsList: List<Stat>
) : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    class StatsViewHolder(val binding: StatListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder =
        StatsViewHolder(
            StatListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val currentItem = statsList[position]
        when (currentItem.type) {
            StatType.TODAY_CLICKS -> {
                holder.binding.statLabel.text = currentItem.name
                holder.binding.statValue.text = currentItem.value
                holder.binding.statIcon.setImageResource(R.drawable.click_fill)
                holder.binding.statIcon.backgroundTintList =
                    ColorStateList.valueOf(holder.binding.root.context.getColor(R.color.today_clicks_background))
            }

            StatType.TOTAL_CLICKS -> {
                holder.binding.statLabel.text = currentItem.name
                holder.binding.statValue.text = currentItem.value
                holder.binding.statIcon.setImageResource(R.drawable.click)
                holder.binding.statIcon.backgroundTintList =
                    ColorStateList.valueOf(holder.binding.root.context.getColor(R.color.total_clicks_background))
            }

            StatType.TOTAL_LINKS -> {
                holder.binding.statLabel.text = currentItem.name
                holder.binding.statValue.text = currentItem.value
                holder.binding.statIcon.setImageResource(R.drawable.link_blue)
                holder.binding.statIcon.backgroundTintList =
                    ColorStateList.valueOf(holder.binding.root.context.getColor(R.color.total_links_background))
            }

            StatType.TOP_LOCATION -> {
                holder.binding.statLabel.text = currentItem.name
                holder.binding.statValue.text = currentItem.value
                holder.binding.statIcon.setImageResource(R.drawable.pin)
                holder.binding.statIcon.backgroundTintList =
                    ColorStateList.valueOf(holder.binding.root.context.getColor(R.color.top_location_background))
            }

            StatType.TOP_SOURCE -> {
                holder.binding.statLabel.text = currentItem.name
                holder.binding.statValue.text = currentItem.value
                holder.binding.statIcon.setImageResource(R.drawable.globe)
                holder.binding.statIcon.backgroundTintList =
                    ColorStateList.valueOf(holder.binding.root.context.getColor(R.color.top_source_background))
            }

            StatType.BEST_TIME -> {
                holder.binding.statLabel.text = currentItem.name
                val startTime = LocalTime.parse(
                    currentItem.value,
                    DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
                )
                val endTime = startTime.plusHours(1)
                holder.binding.statValue.text = "${
                    startTime.format(
                        DateTimeFormatter.ofPattern(
                            "hh:mm",
                            Locale.getDefault()
                        )
                    )
                } - ${endTime.format(DateTimeFormatter.ofPattern("hh:mm", Locale.getDefault()))}"
                holder.binding.statIcon.setImageResource(R.drawable.time)
                holder.binding.statIcon.backgroundTintList =
                    ColorStateList.valueOf(holder.binding.root.context.getColor(R.color.best_time_background))
            }
        }
    }

    override fun getItemCount() = statsList.size

    fun setData(stats: List<Stat>) {
        this.statsList = stats
        notifyDataSetChanged()
    }
}