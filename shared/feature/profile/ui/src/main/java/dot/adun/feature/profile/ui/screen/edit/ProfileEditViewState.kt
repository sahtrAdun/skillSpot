package dot.adun.feature.profile.ui.screen.edit

import androidx.compose.runtime.Immutable
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.ui.entity.TextFieldData

@Immutable
data class ProfileEditViewState(
    val fullName: TextFieldData = TextFieldData(),
    val bio: TextFieldData = TextFieldData(),
    val age: String = "",
    val country: TextFieldData = TextFieldData(),
    val city: TextFieldData = TextFieldData(),
    val avatarUrl: String? = null,
    val hasPickedAvatar: Boolean = false,
    val loadState: LoadState = LoadState.NotStarted,
    val saveState: LoadState = LoadState.NotStarted,
) {
    val isValid: Boolean
        get() = fullName.value.isNotBlank()
}

sealed interface ProfileEditScreenResult {
    data object Finish : ProfileEditScreenResult
    data object Saved : ProfileEditScreenResult
}
