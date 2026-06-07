package dot.adun.feature.profile.ui.screen.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dot.adun.core.domain.entity.resRef
import dot.adun.core.domain.mappers.isLoading
import dot.adun.core.ui.components.ContentLabel
import dot.adun.core.ui.components.FloatingAppBar
import dot.adun.core.ui.components.FullScreenLoader
import dot.adun.core.ui.components.MaterialShape
import dot.adun.core.ui.components.VSpacer
import dot.adun.core.ui.components.base.AdunScaffold
import dot.adun.core.ui.components.base.AppScreen
import dot.adun.core.ui.components.base.BorderVisibility
import dot.adun.core.ui.components.buttons.PrimaryButton
import dot.adun.core.ui.components.buttons.rememberButtonState
import dot.adun.core.ui.components.textFields.SimpleTextField
import dot.adun.core.ui.entity.MaterialDecorator
import dot.adun.core.ui.entity.TextFieldData
import dot.adun.core.ui.modifiers.click.Clickable
import dot.adun.core.ui.modifiers.click.clickableEffect
import dot.adun.core.ui.modifiers.surface
import dot.adun.core.ui.theme.AppTheme
import dot.adun.feature.profile.ui.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileEditScreen(
    viewModel: ProfileEditViewModel,
) = AppScreen(viewModel) { state, intents ->
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var pickedUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        uri ?: return@rememberLauncherForActivityResult
        pickedUri = uri
        scope.launch {
            val bytes = withContext(Dispatchers.IO) {
                context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            }
            bytes?.let { intents.pickAvatar(it) }
        }
    }

    ProfileEditForm(
        fullName = state.fullName,
        bio = state.bio,
        age = state.age,
        country = state.country,
        city = state.city,
        avatarModel = pickedUri ?: state.avatarUrl,
        saveState = state.saveState,
        enabled = !state.loadState.isLoading,
        onPickAvatar = {
            launcher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        },
        intents = intents,
    )

    FullScreenLoader(isLoading = state.loadState.isLoading)
}

@Composable
private fun ProfileEditForm(
    fullName: TextFieldData,
    bio: TextFieldData,
    age: String,
    country: TextFieldData,
    city: TextFieldData,
    avatarModel: Any?,
    saveState: dot.adun.core.domain.entity.LoadState,
    enabled: Boolean,
    onPickAvatar: () -> Unit,
    intents: ProfileEditViewIntents,
) {
    AdunScaffold(
        appBar = {
            FloatingAppBar(
                label = stringResource(R.string.profile_edit_title),
                onBackClick = intents.navigateBack,
            )
        },
        bottomContent = {
            PrimaryButton(
                state = rememberButtonState(
                    loading = saveState.isLoading,
                    disabled = !enabled,
                ),
                clickable = Clickable.of(intents.save),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.profile_save))
            }
        }
    ) { padding ->
        MaterialShape(
            size = MaterialDecorator.Size.Large,
            color = AppTheme.colors.layer.onSurface,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(top = padding.top)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            VSpacer(12.dp)

            AvatarPicker(
                model = avatarModel,
                enabled = enabled,
                onClick = onPickAvatar,
            )

            ContentLabel(label = resRef(R.string.profile_full_name)) {
                SimpleTextField(
                    data = fullName,
                    onValueChange = intents.changeFullName,
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    borderVisibility = BorderVisibility.Always,
                )
            }

            ContentLabel(label = resRef(R.string.profile_bio)) {
                SimpleTextField(
                    data = bio,
                    onValueChange = intents.changeBio,
                    enabled = enabled,
                    minLines = 4,
                    maxLines = 4,
                    borderVisibility = BorderVisibility.Always,
                )
            }

            ContentLabel(label = resRef(R.string.profile_age)) {
                SimpleTextField(
                    data = TextFieldData(value = age),
                    onValueChange = intents.changeAge,
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    borderVisibility = BorderVisibility.Always,
                )
            }

            ContentLabel(label = resRef(R.string.profile_country)) {
                SimpleTextField(
                    data = country,
                    onValueChange = intents.changeCountry,
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    borderVisibility = BorderVisibility.Always,
                )
            }

            ContentLabel(label = resRef(R.string.profile_city)) {
                SimpleTextField(
                    data = city,
                    onValueChange = intents.changeCity,
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    borderVisibility = BorderVisibility.Always,
                )
            }

            VSpacer(padding.bottom)
        }
    }
}

@Composable
private fun AvatarPicker(
    model: Any?,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val clickable = Clickable.of { if (enabled) onClick() }

    Box(contentAlignment = Alignment.BottomEnd) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .requiredSize(96.dp)
                .clip(CircleShape)
                .surface(
                    color = AppTheme.colors.layer.onSurface,
                    shape = CircleShape,
                )
                .clickableEffect(clickable),
        ) {
            if (model != null) {
                AsyncImage(
                    model = model,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .requiredSize(96.dp)
                        .clip(CircleShape),
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = AppTheme.colors.icon.secondary,
                    modifier = Modifier.requiredSize(64.dp),
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .surface(
                    color = AppTheme.colors.layer.primary,
                    shape = CircleShape,
                    padding = 6.dp,
                )
                .clickableEffect(clickable),
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = null,
                tint = AppTheme.colors.icon.onPrimary,
                modifier = Modifier.requiredSize(16.dp),
            )
        }
    }
}
