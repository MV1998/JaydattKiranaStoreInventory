package com.mv1998.jaydatt_kiranastore.data.local

data class Month(
    val id: Int,
    val name: String
)

object MonthMemoryDB {
    val monthList = listOf(
        Month(0, "January"),
        Month(1, "February"),
        Month(2, "March"),
        Month(3, "April"),
        Month(4, "May"),
        Month(5, "June"),
        Month(6, "July"),
        Month(7, "August"),
        Month(8, "September"),
        Month(9, "October"),
        Month(10, "November"),
        Month(11, "December"),
    )
}