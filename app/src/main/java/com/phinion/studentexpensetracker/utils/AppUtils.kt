package com.phinion.studentexpensetracker.utils

import java.util.Calendar

object AppUtils{
    fun getGreetText(): String{
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        if (hour in 5..11){
            return "Good Morning \uD83D\uDC4B"
        }else if (hour in 12..18){
            return "Good Afternoon \uD83D\uDC4B"
        }else{
            return "Good Evening \uD83D\uDC4B"
        }
    }
}