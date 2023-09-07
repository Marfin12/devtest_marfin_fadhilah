package com.marfin_fadhilah.devtest.core.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.marfin_fadhilah.devtest.R
import kotlinx.android.synthetic.main.employee_info_edittext.view.*

class EmployeeInfoEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var txtLabel: TextView
    private lateinit var edtValue: EditText
    private lateinit var edtBackground: Drawable

    var label = ""
    var value = ""

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.employee_info_edittext, this)

        txtLabel = findViewById(R.id.tv_item_label)
        edtValue = findViewById(R.id.edt_item_value)
        edtBackground = edt_item_value.background

        edt_item_value.background = null

        val attribute = context.obtainStyledAttributes(attrs, R.styleable.EmployeeInfoEditText)
        try {
            txtLabel.text = attribute.getString(R.styleable.EmployeeInfoEditText_label)
            edtValue.setText(attribute.getString(R.styleable.EmployeeInfoEditText_value))

            label = txtLabel.text.toString()
            value = edtValue.text.toString()
        } finally {
            attribute.recycle()
        }
    }

    fun setLabelAndValue(label: String, value: String) {
        this.label = label
        this.value = value
        txtLabel.text = label
        edtValue.setText(value)
    }

    fun setEnable(isEnabled: Boolean) {
        this.edt_item_value.isEnabled = isEnabled

        if (isEnabled) {
            this.edt_item_value.background = edtBackground
        } else {
            this.edt_item_value.background = null
        }
    }
}