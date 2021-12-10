package com.example.codechallenge2.model

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.codechallenge2.R
import com.example.codechallenge2.extensions.parse
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

data class DisplayCard(val title: TextField? = null, val description: TextField? = null, val image: ImageJson? = null) {
    val hasDescription: Boolean
        get() = description != null

    val hasImage: Boolean
        get() = image != null
}

@ExperimentalUnitApi
@Composable
fun ComposeCard(card: DisplayCard) {
    Card(

        elevation = 4.dp,
    ) {
        card.image?.url?.let { url ->
            val image = loadPicture(url = url, defaultImage = R.drawable.empty_plate).value
            image?.let { img ->
                Log.i("Image", "$img")
                Image(
                    bitmap = img.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "1"
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            card.title?.let { title ->
                val fontSize = title.attributes?.getTextSize() ?: 12
                val colorCode = title.attributes?.color ?: "#FFF"
                val color = Color(android.graphics.Color.parseColor(colorCode))
                Text(
                    "${title.text}",
                    fontSize = TextUnit(fontSize.toFloat(), TextUnitType.Sp),
                    color = color
                )
            }
            card.description?.let { description ->
                val fontSize = description.attributes?.getTextSize() ?: 12
                val colorCode = description.attributes?.color ?: "#FFF"
                val color = Color(android.graphics.Color.parseColor(colorCode))
                Text(
                    "${description.text}",
                    fontSize = TextUnit(fontSize.toFloat(), TextUnitType.Sp),
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