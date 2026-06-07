package dot.adun.feature.profile.ui.screen.edit

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.profile.domain.ProfileModel
import dot.adun.feature.profile.domain.entity.ProfileUpdate
import dot.adun.feature.profile.ui.R
import javax.inject.Inject

@Stable
@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val model: ProfileModel,
) : StateViewModel<ProfileEditViewState, ProfileEditViewIntents, ProfileEditScreenResult>(
    ProfileEditViewState()
) {
    override val intents = ProfileEditViewIntents()

    /** Raw bytes of a freshly picked avatar, pending upload on save. */
    private var pickedAvatar: ByteArray? = null

    init {
        onIntent(intents.navigateBack) {
            emitResult(ProfileEditScreenResult.Finish)
        }

        onIntent(intents.changeFullName) { name ->
            update { state -> state.copy(fullName = state.fullName.update(name)) }
        }

        onIntent(intents.changeBio) { bio ->
            update { state -> state.copy(bio = state.bio.update(bio)) }
        }

        onIntent(intents.changeAge) { age ->
            update { state -> state.copy(age = age.filter { it.isDigit() }) }
        }

        onIntent(intents.changeCountry) { country ->
            update { state -> state.copy(country = state.country.update(country)) }
        }

        onIntent(intents.changeCity) { city ->
            update { state -> state.copy(city = state.city.update(city)) }
        }

        onIntent(intents.pickAvatar) { bytes ->
            pickedAvatar = bytes
            update { state -> state.copy(hasPickedAvatar = true) }
        }

        onIntent(intents.save) {
            action { state ->
                if (!state.isValid) return@action
                performSave(state)
            }
        }

        loadProfile()
    }

    private fun loadProfile() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { model.readProfile() }
            onSuccess { profile ->
                profile ?: return@onSuccess
                update { state ->
                    state.copy(
                        fullName = state.fullName.update(profile.personalInfo.fullName ?: ""),
                        bio = state.bio.update(profile.personalInfo.bio ?: ""),
                        age = profile.personalInfo.age?.toString() ?: "",
                        country = state.country.update(profile.personalInfo.country ?: ""),
                        city = state.city.update(profile.personalInfo.city ?: ""),
                        avatarUrl = profile.avatarUrl,
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }

    private fun performSave(state: ProfileEditViewState) {
        task(
            stateRead = { vs -> vs.saveState },
            stateWrite = { vs, ls -> vs.copy(saveState = ls) }
        ) {
            job {
                val avatarUrl = resolveAvatarUrl(state.avatarUrl)
                model.updateMyProfile(
                    ProfileUpdate(
                        fullName = state.fullName.value.trim().ifBlank { null },
                        bio = state.bio.value.trim().ifBlank { null },
                        age = state.age.toIntOrNull()?.toShort(),
                        country = state.country.value.trim().ifBlank { null },
                        city = state.city.value.trim().ifBlank { null },
                        avatarUrl = avatarUrl,
                    )
                )
            }
            onSuccess {
                simpleSnackbar(resRef(R.string.profile_updated))
                emitResult(ProfileEditScreenResult.Saved)
            }
            onError { errorSnack(it) }
        }
    }

    /**
     * Uploads a freshly picked avatar if present. If the upload fails the chain
     * is NOT aborted — a snackbar is shown and the previous avatar url is kept.
     */
    private suspend fun resolveAvatarUrl(currentUrl: String?): String? {
        val bytes = pickedAvatar ?: return currentUrl
        return try {
            model.uploadAvatar(bytes)
        } catch (e: Exception) {
            simpleSnackbar(resRef(R.string.profile_avatar_upload_failed), isError = true)
            currentUrl
        }
    }
}
