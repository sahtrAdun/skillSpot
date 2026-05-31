package dot.adun.feature.authorized.ui.screen.active

import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.ui.core.StateViewModel
import dot.adun.feature.authorized.domain.AuthorizedModel
import dot.adun.feature.authorized.domain.entity.buildPagingParams
import dot.adun.feature.profile.domain.ProfileModel
import javax.inject.Inject

@Stable
@HiltViewModel
class ActiveViewModel @Inject constructor(
    private val profileModel: ProfileModel,
    private val model: AuthorizedModel
) : StateViewModel<ActiveViewState, ActiveViewIntents, ActiveScreenResult>(ActiveViewState()) {
    override val intents = ActiveViewIntents()

    init {
        onIntent(intents.openDetails) {
            emitResult(ActiveScreenResult.Details)
        }

        on(profileModel.profile) { profile ->
            update { state -> state.copy(userRole = profile?.role ?: UserRole.None) }
            action { state ->
                if (state.emptyForUser()) { resolveUserRoleActions() }
            }
        }
    }

    private fun resolveUserRoleActions() {
        when (state.value.userRole) {
            UserRole.Freelancer -> performFreelancers()
            UserRole.Customer -> performCustomers()
            UserRole.None -> Unit
        }
    }

    private fun performFreelancers(skipCache: Boolean = false) {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { state ->
                val params = buildPagingParams(
                    listSize = state.vacancies.size,
                    skipCache = skipCache
                )

                model.getUserActiveVacancies(params)
            }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        vacancies = state.vacancies + paging.data
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }

    private fun performCustomers(skipCache: Boolean = false) {
        task(
            stateRead = { vs -> vs.loadState },
            stateWrite = { vs, ls -> vs.copy(loadState = ls) }
        ) {
            job { state ->
                val params = buildPagingParams(
                    listSize = state.vacancies.size,
                    skipCache = skipCache
                )

                model.getClientActiveProjects(params)
            }
            onSuccess { paging ->
                update { state ->
                    state.copy(
                        activeProjects = state.activeProjects + paging.data
                    )
                }
            }
            onError { errorSnack(it) }
        }
    }
}

sealed interface ActiveScreenResult {
    data object Details : ActiveScreenResult
    data object Finish : ActiveScreenResult
}
