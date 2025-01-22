package mega.triple.aaa.ui.ext

import androidx.annotation.DrawableRes
import mega.triple.aaa.ui.R.drawable

@DrawableRes
fun getAccuWeatherIconRes(iconId: Int?): Int = when (iconId) {
    1 -> drawable.ac_1
    2 -> drawable.ac_2
    3 -> drawable.ac_3
    4 -> drawable.ac_4
    5 -> drawable.ac_5
    6 -> drawable.ac_6
    7 -> drawable.ac_7
    8 -> drawable.ac_8
    11 -> drawable.ac_11
    12 -> drawable.ac_12
    13 -> drawable.ac_13
    14 -> drawable.ac_14
    15 -> drawable.ac_15
    16 -> drawable.ac_16
    17 -> drawable.ac_17
    18 -> drawable.ac_18
    19 -> drawable.ac_19
    20 -> drawable.ac_20
    21 -> drawable.ac_21
    22 -> drawable.ac_22
    23 -> drawable.ac_23
    24 -> drawable.ac_24
    25 -> drawable.ac_25
    26 -> drawable.ac_26
    29 -> drawable.ac_29
    30 -> drawable.ac_30
    31 -> drawable.ac_31
    32 -> drawable.ac_32
    33 -> drawable.ac_33
    34 -> drawable.ac_34
    35 -> drawable.ac_35
    36 -> drawable.ac_36
    37 -> drawable.ac_37
    38 -> drawable.ac_38
    39 -> drawable.ac_39
    40 -> drawable.ac_40
    41 -> drawable.ac_41
    42 -> drawable.ac_42
    43 -> drawable.ac_43
    44 -> drawable.ac_44
    else -> drawable.img_weather_temp
}
