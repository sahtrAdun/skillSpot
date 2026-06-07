package dot.adun.feature.authorized.ui.screen.applications

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.LoadState
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.domain.entity.resRef
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.buildPagingParams
import dot.adun.feature.authorized.ui.R
import dot.adun.feature.profile.domain.ProfileModel
import javax.inject.Inject

@Stable
@HiltViewModel
class ApplicationsViewModel @Inject constructor(
    private val model: AuthorizedModel,
    private val profileModel: ProfileModel,
) : StateViewModel<ApplicationsViewState, ApplicationsViewIntents, ApplicationsScreenResult>(
    ApplicationsViewState()
) {
    override val intents = ApplicationsViewIntents()

    init {
        onIntent(intents.navigateBack) {
            emitResult(ApplicationsScreenResult.Finish)
        }

        onIntent(intents.openDetails) { (isVacancy, id) ->
            emitResult(ApplicationsScreenResult.Details(isVacancy = isVacancy, id = id))
        }

        onIntent(intents.decline) { applicationId ->
            cancel(applicationId)
        }

        onIntent(intents.approve) { applicationId ->
            approve(applicationId)
        }

        on(profileModel.profile) { profile ->
            val role = profile?.role ?: UserRole.None
            update { state -> state.copy(userRole = role) }
            action { state ->
                if (state.loadState == LoadState.NotStarted) {
                    load(role)
                }
            }
        }
    }

    private fun load(role: UserRole) = when (role) {
        UserRole.Freelancer -> loadMyApplications()
        UserRole.Customer -> loadIncomingApplications()
        UserRole.None -> Unit
    }

    private fun loadMyApplications() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { model.getMyApplications(buildPagingParams(listSize = 0)) }
            onSuccess { applications ->
                update { state -> state.copy(myApplications = applications) }
            }
            onError { errorSnack(it) }
        }
    }

    private fun loadIncomingApplications() {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { model.getClientIncomingApplications(buildPagingParams(listSize = 0)) }
            onSuccess { applications ->
                update { state -> state.copy(incomingApplications = applications) }
            }
            onError { errorSnack(it) }
        }
    }

    private fun approve(applicationId: String) {
        update { state -> state.copy(approvingId = applicationId) }
        task(
            stateRead = { vs -> vs.acceptState },
            stateWrite = { vs, ls -> vs.copy(acceptState = ls) }
        ) {
            job { model.acceptApplication(applicationId) }
            onSuccess { projectId ->
                update { state ->
                    state.copy(
                        approvingId = null,
                        incomingApplications = state.incomingApplications
                            .filterNot { it.applicationId == applicationId },
                    )
                }
                simpleSnackbar(resRef(R.string.application_approved))
                emitResult(ApplicationsScreenResult.OpenProject(projectId))
            }
            onError {
                update { state -> state.copy(approvingId = null) }
                errorSnack(it)
            }
        }
    }

    private fun cancel(applicationId: String) {
        task(
            stateRead = { vs -> vs.cancelState },
            stateWrite = { vs, ls -> vs.copy(cancelState = ls) }
        ) {
            job { model.cancelApplication(applicationId) }
            onSuccess {
                update { state ->
                    state.copy(
                        myApplications = state.myApplications
                            .filterNot { it.applicationId == applicationId },
                    )
                }
                simpleSnackbar(resRef(R.string.application_cancelled))
            }
            onError { errorSnack(it) }
        }
    }
}
