package com.example.yangchaoming.bappdemo.video.videoTrim




class VideoUtil {
    companion object{



//         fun convertSecondsToTime(seconds: Long): String {
//            var timeStr: String? = null
//            var hour = 0
//            var minute = 0
//            var second = 0
//            if (seconds <= 0) {
//                return "00:00"
//            } else {
//                minute = seconds.toInt() / 60
//                if (minute < 60) {
//                    second = seconds.toInt() % 60
//                    timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second)
//                } else {
//                    hour = minute / 60
//                    if (hour > 99) return "99:59:59"
//                    minute = minute % 60
//                    second = (seconds - (hour * 3600).toLong() - (minute * 60).toLong()).toInt()
//                    timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second)
//                }
//            }
//            return timeStr
//        }

        fun convertSecondsToTime(seconds: Long): String{
            val hr = seconds / 3600
            val rem = seconds % 3600
            val mn = rem / 60
            val sec = rem % 60
            return String.format("%02d", hr) + ":" + String.format("%02d", mn) + ":" + String.format("%02d", sec)
        }

         fun unitFormat(i: Int): String {
            var retStr: String? = null
            if (i >= 0 && i < 10) {
                retStr = "0" + Integer.toString(i)
            } else {
                retStr = "" + i
            }
            return retStr
        }
    }

}