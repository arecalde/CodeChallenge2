package com.example.codechallenge2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import com.example.codechallenge2.R
import com.example.codechallenge2.loadPicture


open class JsonCard {
    @SerializedName("image")
    @Expose
    var image: ImageJson? = null

    @SerializedName("title")
    @Expose
    var title: TextField? = null

    @SerializedName("description")
    @Expose
    var description: TextField? = null

    @SerializedName("value")
    @Expose
    var text: String? = null

    @SerializedName("attributes")
    @Expose
    var attributes: TextAttributes? = null
}

data class DisplayCard(val title: TextField? = null, val description: TextField? = null, val image: ImageJson? = null)

@Composable
fun ImageCard(card: DisplayCard) {

    card.image?.url?.let { url ->
        val image = loadPicture(url = url, defaultImage = R.drawable.empty_plate).value
        image?.let { img ->
            Card(
                modifier = Modifier.fillMaxSize(),
                elevation = 4.dp
            ) {

                Image(
                    bitmap = img.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "1"
                )


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxSize()
                        .border(BorderStroke(2.dp, Color.Red))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    card.title?.let { title ->
                        val fontSize = title.attributes?.getTextSize() ?: 12
                        val colorCode = title.attributes?.color ?: "#FFF"
                        val color = Color(android.graphics.Color.parseColor(colorCode))
                        Text(
                            "${title.text}",
                            style = TextStyle(color = color, fontSize = fontSize.sp),
                            color = color
                        )
                    }
                    card.description?.let { description ->
                        val fontSize = description.attributes?.getTextSize() ?: 12
                        val colorCode = description.attributes?.color ?: "#FFF"
                        val color = Color(android.graphics.Color.parseColor(colorCode))
                        Text(
                            "${description.text}",
                            style = TextStyle(color = color, fontSize = fontSize.sp),
                            color = color
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextCard(card: DisplayCard) {
    Card(
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            card.title?.let { title ->
                val fontSize = title.attributes?.getTextSize() ?: 12
                val colorCode = title.attributes?.color ?: "#FFF"
                val color = Color(android.graphics.Color.parseColor(colorCode))
                Text(
                    "${title.text}",
                    style = TextStyle(color = color, fontSize = fontSize.sp),
                    color = color
                )
            }
            card.description?.let { description ->
                val fontSize = description.attributes?.getTextSize() ?: 12
                val colorCode = description.attributes?.color ?: "#FFF"
                val color = Color(android.graphics.Color.parseColor(colorCode))
                Text(
                    "${description.text}",
                    style = TextStyle(color = color, fontSize = fontSize.sp),
                    color = color
                )
            }
        }

    }
}

class CardWrapper {
    @SerializedName("card_type")
    @Expose
    var cardType: String? = null

    @SerializedName("card")
    @Expose
    var card: JsonCard? = null
}