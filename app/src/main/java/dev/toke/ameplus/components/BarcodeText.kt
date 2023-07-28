package dev.toke.ameplus.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun BarcodeText(
    text: TextFieldValue,
    label: String,
    modifier: Modifier,
    enabled: Boolean,
    style: TextStyle
) {
    OutlinedTextField(value = text,
        onValueChange = { /* NOTHING */ },
        label = { Text(label, style = MaterialTheme.typography.titleSmall) },
        placeholder = { Text("BARCODE", style = MaterialTheme.typography.headlineLarge) },
        modifier = modifier,
        enabled = enabled,
        textStyle = style,
    )
}