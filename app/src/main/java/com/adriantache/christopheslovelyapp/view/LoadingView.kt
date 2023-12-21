package com.adriantache.christopheslovelyapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "🈶",
            fontSize = 64.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
