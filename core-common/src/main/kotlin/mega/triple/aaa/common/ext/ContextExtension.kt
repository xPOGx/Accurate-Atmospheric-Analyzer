package mega.triple.aaa.common.ext

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String?) =
    Toast.makeText(
        this,
        message.orEmpty(),
        Toast.LENGTH_LONG
    ).show()