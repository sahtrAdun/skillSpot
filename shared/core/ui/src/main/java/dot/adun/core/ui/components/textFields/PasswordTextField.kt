package dot.adun.core.ui.components.textFields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dot.adun.common.resources.Res
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.entity.TextFieldData

@Composable
fun PasswordTextField(
    data: TextFieldData,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    actions: KeyboardActions? = null,
    keyboardType: KeyboardType = KeyboardType.Password,
    borderVisibility: BorderVisibility = BorderVisibility.Newer,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = keyboardType),
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val icon = if (passwordVisible) Res.drawable.ic_eye_fill_closed_24
    else Res.drawable.ic_eye_fill_open_24

    val visualTransformation = if (passwordVisible) VisualTransformation.None
    else PasswordVisualTransformation()

    SimpleTextField(
        data = data,
        onValueChange = onValueChange,
        modifier = modifier,
        trailingIcon = icon,
        onTrailingClick = { passwordVisible = !passwordVisible },
        visualTransformation = visualTransformation,
        placeholder = "•••••••••••••••",
        actions = actions,
        keyboardType = keyboardType,
        keyboardOptions = keyboardOptions,
        borderVisibility = borderVisibility,
    )
}
