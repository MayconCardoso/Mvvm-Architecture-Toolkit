package com.mctech.library.view.ktx

import android.content.ClipData
import android.content.ClipboardManager
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat

fun EditText.getValue() = text.toString().trim()

fun EditText.observeActionKeyPressed(actionKeyCode: Int, block: () -> Unit) {
    setOnEditorActionListener(object : TextView.OnEditorActionListener {
        override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == actionKeyCode) {
                block()
                return true
            }
            return false
        }
    })
}

fun EditText.observeActionDonePressed(block: () -> Unit) = this.observeActionKeyPressed(EditorInfo.IME_ACTION_DONE){
    block()
}

fun TextView.copyValueToClipboard(hint : String = ""){
    val clipboard: ClipboardManager? = ContextCompat.getSystemService<ClipboardManager>(
            context,
            ClipboardManager::class.java
    )

    clipboard?.let {
        val clip = ClipData.newPlainText(hint, text)
        clipboard.setPrimaryClip(clip)
    }
}