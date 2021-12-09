package com.example.codechallenge2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Page {
    @SerializedName("cards")
    @Expose
    var cards: List<CardWrapper>? = null
}

class PageWrapper {
    @SerializedName("page")
    @Expose
    var page: Page? = null
}