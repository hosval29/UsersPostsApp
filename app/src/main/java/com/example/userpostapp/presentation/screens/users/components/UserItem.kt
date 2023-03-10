package com.example.userpostapp.presentation.screens.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.userpostapp.R
import com.example.userpostapp.domain.model.User

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    onNavigateSeePostClick: (Int, String) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .background(Color.White)
            .clip(RoundedCornerShape(2.dp))
            .shadow(elevation = 2.dp)
    ) {
        val (name, boxPhone, boxEmail, buttonSeePost) = createRefs()

        val startGuideline = createGuidelineFromStart(16.dp)
        val topGuideline = createGuidelineFromTop(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)

        Text(
            text = user.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color(0xFF004A50),
            modifier = Modifier.constrainAs(name) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                top.linkTo(topGuideline)
                width = Dimension.fillToConstraints
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.constrainAs(boxPhone) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                top.linkTo(name.bottom, margin = 4.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = user.phone,
                tint = Color(0xFF004A50)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = user.phone,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.constrainAs(boxEmail) {
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                top.linkTo(boxPhone.bottom, margin = 4.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = user.email,
                tint = Color(0xFF004A50)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = user.email,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        TextButton(
            onClick = {
                onNavigateSeePostClick(user.id, user.name)
            },
            modifier = Modifier.constrainAs(buttonSeePost) {
                top.linkTo(boxEmail.bottom, margin = 8.dp)
                end.linkTo(endGuideline)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        ) {
            Text(
                text = stringResource(id = R.string.button_see_post).uppercase(),
                color = Color(0xFF004A50)
            )
        }
    }
}
