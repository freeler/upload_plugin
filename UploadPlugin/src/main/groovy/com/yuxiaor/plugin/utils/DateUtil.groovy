package com.yuxiaor.plugin.utils

import java.text.SimpleDateFormat


class DateUtil {

    static String format(long timeMills) {
        return new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(timeMills)
    }

}