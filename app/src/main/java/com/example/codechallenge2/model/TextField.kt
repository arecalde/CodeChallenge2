package com.example.codechallenge2.model

import android.graphics.Color
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TextField {
    @SerializedName("value")
    @Expose
    var text: String? = null

    @SerializedName("attributes")
    @Expose
    var attributes: TextAttributes? = null
}

class TextAttributes {
    @SerializedName("text_color")
    @Expose
    var color: String? = null

    @SerializedName("font")
    @Expose
    var font: Font? = null

    fun getTextColor() = Color.parseColor(color ?: "#FFF")
    fun getTextSize() = font?.size ?: 12

}

class Font {
    @SerializedName("size")
    @Expose
    var size: Int? = null
}

