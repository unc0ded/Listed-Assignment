package com.unc0ded.listedapp.view.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.tabs.TabLayout
import com.unc0ded.listedapp.R
import com.unc0ded.listedapp.databinding.FragmentDashboardBinding
import com.unc0ded.listedapp.model.LinkDetail
import com.unc0ded.listedapp.model.Stat
import com.unc0ded.listedapp.model.StatType
import com.unc0ded.listedapp.utils.ClicksYAxisValueFormatter
import com.unc0ded.listedapp.utils.DatesXAxisValueFormatter
import com.unc0ded.listedapp.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<DashboardViewModel>()
    private val statsAdapter by lazy { StatsAdapter(emptyList()) }
    private val linksAdapter by lazy { LinksAdapter(emptyList()) }
    private val statsList = mutableListOf<Stat>()
    private var recentLinksList = listOf<LinkDetail>()
    private var topLinksList = listOf<LinkDetail>()
    private val dates = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getDashboard()
        setupRecyclerViews()
        setupObservers()
        setupLineChart()
        val currentDateTime = LocalDateTime.now()

        val currentTime = currentDateTime.toLocalTime()
        if (currentTime < LocalTime.parse("12:00:00")) {
            binding.greetingTv.text = "Good Morning"
        } else if (currentTime < LocalTime.parse("16:00:00")) {
            binding.greetingTv.text = "Good Afternoon"
        } else if (currentTime < LocalTime.parse("20:00:00")) {
            binding.greetingTv.text = "Good Evening"
        } else {
            binding.greetingTv.text = "Good Night"
        }

        val currentDate = currentDateTime.toLocalDate()
        var cursor = currentDate.minusDays(30)
        while (!cursor.isAfter(currentDate)) {
            dates.add(cursor.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            cursor = cursor.plusDays(1)
        }
    }

    private fun setupLineChart() {
        with(binding.monthlyChart) {
            setTouchEnabled(true)
            setPinchZoom(true)
            isAutoScaleMinMaxEnabled = true
            isDoubleTapToZoomEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawAxisLine(false)
            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 30f
            xAxis.setLabelCount(6, true)
            xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.text_trunks)
            xAxis.isGranularityEnabled = true
            xAxis.labelRotationAngle = 0f
            xAxis.valueFormatter = DatesXAxisValueFormatter(dates)
            axisRight.isEnabled = false
            axisLeft.setDrawAxisLine(false)
            axisLeft.axisMinimum = -1f
            axisLeft.valueFormatter = ClicksYAxisValueFormatter()
            axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.text_trunks)
            axisLeft.isGranularityEnabled = true
        }
    }

    private fun setupObservers() {
        viewModel.dashboardResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                statsList.clear()
                statsList.add(
                    Stat(
                        "Today's Clicks",
                        response.todayClicks.toString(),
                        StatType.TODAY_CLICKS
                    )
                )
                statsList.add(
                    Stat(
                        "Total Clicks",
                        response.totalClicks.toString(),
                        StatType.TOTAL_CLICKS
                    )
                )
                statsList.add(
                    Stat(
                        "Total Links",
                        response.totalLinks.toString(),
                        StatType.TOTAL_LINKS
                    )
                )
                statsList.add(
                    Stat(
                        "Top Source",
                        response.topSource.ifBlank { "N/A" },
                        StatType.TOP_SOURCE
                    )
                )
                statsList.add(
                    Stat(
                        "Top Location",
                        response.topLocation.ifBlank { "N/A" },
                        StatType.TOP_LOCATION
                    )
                )
                statsList.add(
                    Stat(
                        "Best Time",
                        response.startTime.ifBlank { "N/A" },
                        StatType.BEST_TIME
                    )
                )
                statsAdapter.setData(statsList)

                recentLinksList = response.data.recentLinks
                topLinksList = response.data.topLinks
                if (binding.tabLinks.selectedTabPosition == 0) {
                    linksAdapter.setData(topLinksList)
                } else {
                    linksAdapter.setData(recentLinksList)
                }

                val chartValues = arrayListOf<Entry>()
                for (i in dates.indices) {
                    chartValues.add(
                        Entry(
                            i.toFloat(),
                            response.data.overallUrlChart[dates[i]]?.toFloat() ?: 0f
                        )
                    )
                }

                val dataSet: LineDataSet
                if (binding.monthlyChart.data != null && binding.monthlyChart.data.dataSetCount > 0) {
                    dataSet = binding.monthlyChart.data.getDataSetByIndex(0) as LineDataSet
                    dataSet.values = chartValues
                } else {
                    dataSet = LineDataSet(chartValues, "Daily Clicks")
                    dataSet.mode = LineDataSet.Mode.LINEAR
                    dataSet.setDrawFilled(true)
                    dataSet.setDrawValues(false)
                    dataSet.isHighlightEnabled = false
                    dataSet.color = ContextCompat.getColor(requireContext(), R.color.text_link)
                    dataSet.lineWidth = 1.5f
                    dataSet.fillDrawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.chart_gradient)
                    dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.text_link))
                    dataSet.circleRadius = 3f

                    ArrayList<ILineDataSet>().apply {
                        add(dataSet)
                    }.also {
                        binding.monthlyChart.data = LineData(it)
                        binding.monthlyChart.animateXY(1000, 1000)
                    }
                }

                binding.btnContactWhatsapp.setOnClickListener {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://wa.me/91${response.whatsappNumber}")
                        )
                    )
                }
            }
        }

        binding.tabLinks.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> linksAdapter.setData(topLinksList)
                    1 -> linksAdapter.setData(recentLinksList)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupRecyclerViews() {
        binding.statsRv.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.statsRv.addItemDecoration(
            SpaceItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.horizontal_margin
                ), RecyclerView.HORIZONTAL
            )
        )
        binding.rvLinks.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvLinks.addItemDecoration(
            SpaceItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.vertical_margin
                ), RecyclerView.VERTICAL
            )
        )
        binding.statsRv.adapter = statsAdapter
        binding.rvLinks.adapter = linksAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}