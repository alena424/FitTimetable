package com.tam.fittimetable.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.ZoneId
import java.util.*

inline fun <reified T : View> Activity.lazyView(
    @IdRes viewId: Int
): Lazy<T> = lazy { findViewById<T>(viewId) }

inline fun <reified T : View> Fragment.lazyView(
    @IdRes viewId: Int
): Lazy<T> = lazy { requireActivity().findViewById<T>(viewId) }

fun org.threeten.bp.LocalDate.toCalendar(): Calendar {
    val instant = atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    val calendar = Calendar.getInstance()
    calendar.time = DateTimeUtils.toDate(instant)
    return calendar
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observe: (T) -> Unit) {
    observe(owner, Observer { observe(it) })
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
