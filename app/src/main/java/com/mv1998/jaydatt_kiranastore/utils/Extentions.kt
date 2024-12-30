package com.mv1998.jaydatt_kiranastore.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Formatter {
    fun getCurrencyFormat() : NumberFormat {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en", "in"))
        return numberFormat
    }

    fun getDateFormat() : SimpleDateFormat {
        return SimpleDateFormat("dd/MM/yyyy")
    }

    fun getDateTimeFormat() : SimpleDateFormat {
        return SimpleDateFormat("dd/MM/yyyy mm:hh:ss")
    }
}

fun Date.toDateTimeFormat() : String {
    return Formatter.getDateTimeFormat().format(this)
}

fun Date.toDateFormat() : String {
    return Formatter.getDateFormat().format(this)
}

fun String.capitalizeFirstLetter(): String {
    if (this.isEmpty()) return "-"
    return "${this.substring(0, 1).uppercase()}${this.substring(1)}"
}

fun String.toCurrencyFormat() : String {
    return Formatter.getCurrencyFormat().format(BigDecimal("%.2f".format(this.toFloat())))
}