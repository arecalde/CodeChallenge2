package com.example.codechallenge2.model

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout

import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout


open class Card {
    @SerializedName("image")
    @Expose
    var image: Image? = null

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

data class DisplayCard(val title: TextField? = null, val description: TextField? = null, val image: Image? = null) {
    val hasDescription: Boolean
        get() = description != null

    val hasImage: Boolean
        get() = image != null
}

//@Composable
//fun ComposeCard(card: DisplayCard) {
//    androidx.compose.material.Card(shape = MaterialTheme.shapes.small,
//        modifier = Modifier
//            .padding(
//                bottom = 6.dp,
//                top = 6.dp
//            )
//            .fillMaxWidth(),
//        elevation = 8.dp,
//    ) {}
//}

class TextCard: Card() {
    @SerializedName("card_type")
    @Expose
    var cardType: String? = null
}

class CardWrapper {
    @SerializedName("card_type")
    @Expose
    var cardType: String? = null

    @SerializedName("card")
    @Expose
    var card: Card? = null
}
@BindingAdapter("imageUrl")
fun loadImage(view : View, url : String?){
    if (url.isNullOrEmpty()) return
    Picasso.get().load(url).into((view as ImageView))
}

@BindingAdapter("android:textSize")
fun bindTextSize(textView: TextView, size: Int) {
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
}

@BindingAdapter("layout_height")
fun setLayoutHeight(view: View, height: Int) {
    val layoutParams: ViewGroup.LayoutParams = view.layoutParams
    layoutParams.height = height
    view.layoutParams = layoutParams
}