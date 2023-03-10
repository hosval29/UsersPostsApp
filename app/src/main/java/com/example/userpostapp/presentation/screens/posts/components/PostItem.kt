package com.example.userpostapp.presentation.screens.posts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.userpostapp.domain.model.Post

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post
) {
    ConstraintLayout(
        modifier = modifier
            .background(Color.White)
            .clip(RoundedCornerShape(2.dp))
            .shadow(elevation = 2.dp)
    ) {

        val (title, body) = createRefs()

        val startGuideline = createGuidelineFromStart(16.dp)
        val topGuideline = createGuidelineFromTop(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)

        Text(
            text = post.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color(0xFF004A50),
            modifier = Modifier.constrainAs(title) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                top.linkTo(topGuideline)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = post.title,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(body) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                top.linkTo(title.bottom, margin = 4.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

    }
}