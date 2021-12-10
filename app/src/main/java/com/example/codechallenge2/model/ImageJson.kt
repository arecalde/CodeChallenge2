package com.example.codechallenge2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageJson {
    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("size")
    @Expose
    var size: Size? = null
}

class Size {
    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null
}