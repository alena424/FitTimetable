package com.tam.fittimetable.apicient

import android.graphics.Color
import com.alamkanak.weekview.WeekViewDisplayable
import com.alamkanak.weekview.WeekViewEvent
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

/**
 * An event model that was built for automatic serialization from json to object.
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */
data class ApiEvent(
    @Expose
    @SerializedName("name")
    var title: String,
    @Expose
    @SerializedName("id")
    var id: Long,
    @Expose
    @SerializedName("dayOfMonth")
    var dayOfMonth: Int,
    @Expose
    @SerializedName("year")
    var year: Int,
    @Expose
    @SerializedName("month")
    var month: Int,
    @Expose
    @SerializedName("startTime")
    var startTime: String,
    @Expose
    @SerializedName("endTime")
    var endTime: String,
    @Expose
    @SerializedName("color")
    var color: String,
    @Expose
    @SerializedName("room")
    var place: String
) : WeekViewDisplayable<ApiEvent> {

    override fun toWeekViewEvent(): WeekViewEvent<ApiEvent> {
        // Titles have the format "Event 123"
        //val id = title.split(" ").last().toLong()


        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val start = checkNotNull(sdf.parse(startTime))
        val end = checkNotNull(sdf.parse(endTime))

        val now = Calendar.getInstance()
        //dayOfMonth = dayOfMonth -1;

        val startTime = now.clone() as Calendar
        startTime.timeInMillis = start.time
        startTime.set(Calendar.YEAR, year)
        startTime.set(Calendar.MONTH, month)
        startTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val endTime = startTime.clone() as Calendar
        endTime.timeInMillis = end.time
        endTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR))
        endTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH))
        endTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH))

        val color = Color.parseColor(color)
        val style = WeekViewEvent.Style.Builder()
            .setBackgroundColor(color)
            .build()

        return WeekViewEvent.Builder(this)
            .setId(id)
            .setTitle(title)
            .setStartTime(startTime)
            .setEndTime(endTime)
            .setAllDay(false)
            .setStyle(style)
            .setLocation(place)
            .build()
    }
}
