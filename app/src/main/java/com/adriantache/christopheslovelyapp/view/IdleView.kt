package com.adriantache.christopheslovelyapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IdleView(
    linkPrefix: String?,
    onClearLinkPrefix: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(4.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Your lovely link prefix is:",
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(4.dp))

        Text(text = linkPrefix.orEmpty(), fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        Button(onClick = onClearLinkPrefix) {
            Text("Change that lovely")
        }
    }
}

@Preview
@Composable
fun IdleViewPreview() {
    IdleView(
        linkPrefix = "test",
        onClearLinkPrefix = {}
    )
}
