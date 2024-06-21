package com.capstone.glucofie.view.editor

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class ConfirmPasswordEditText : AppCompatEditText {
    @set:JvmName("setOriginalPasswordValue")
    var originalPassword: String? = null
        set(value) {
            field = value
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (originalPassword != null && s.toString() != originalPassword) {
                    setError("Passwords do not match", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    fun setOriginalPassword(password: String) {
        originalPassword = password
    }
}
