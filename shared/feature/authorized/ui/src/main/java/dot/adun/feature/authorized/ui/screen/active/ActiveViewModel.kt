package dot.adun.feature.authorized.ui.screen.active

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dagger.hilt.android.lifecycle.HiltViewModel
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
class ActiveViewModel @Inject constructor(
    private val profileModel: ProfileModel,
    private val model: AuthorizedModel
) : StateViewModel<ActiveViewState, ActiveViewIntents, ActiveScreenResult>(ActiveViewState()) {
    override val intents = ActiveViewIntents()

    private var listeningForCompletion = false

    init {
        onIntent(intents.openDetails) { (isVacancy, id) ->
            emitResult(ActiveScreenResult.Details(isVacancy, id))
        }

        onIntent(intents.openChat) { _ ->
            // TODO: chat is not implemented yet.
        }

        onIntent(intents.complete) { projectId ->
            complete(projectId)
        }

        onIntent(intents.submitReview) { args ->
            submitReview(args)
        }

        onIntent(intents.dismissReview) {
            update { state -> state.copy(reviewProjectId = null) }
        }

        on(profileModel.profile) { profile ->
            update { state ->
                state.copy(
                    userRole = profile?.role ?: UserRole.None,
                    currentUserId = profile?.id,
                )
            }
            action { state ->
                if (state.emptyForUser()) { resolveUserRoleActions() }
                listenForCompletedProjects(state)
            }
        }
    }

    private fun listenForCompletedProjects(state: ActiveViewState) {
        if (listeningForCompletion) return
        if (state.userRole != UserRole.Freelancer) return
        val freelancerId = state.currentUserId ?: return

        listeningForCompletion = true
        on(model.observeCompletedProjects(freelancerId)) { projectId ->
            update { it.copy(reviewProjectId = projectId) }
        }
    }

    private fun complete(projectId: String) {
        update { state -> state.copy(completingProjectId = projectId) }
        task(
            stateRead = { vs -> vs.completeState },
            stateWrite = { vs, ls -> vs.copy(completeState = ls) }
        ) {
            job { model.completeProject(projectId) }
            onSuccess {
                update { state ->
                    state.copy(
                        completingProjectId = null,
                        activeProjects = state.activeProjects
                            .filterNot { it.projectId == projectId },
                        reviewProjectId = projectId,
                    )
                }
            }
            onError {
                update { state -> state.copy(completingProjectId = null) }
                errorSnack(it)
            }
        }
    }

    private fun submitReview(args: ReviewArgs) {
        task(
            stateRead = { vs -> vs.reviewState },
            stateWrite = { vs, ls -> vs.copy(reviewState = ls) }
        ) {
            job {
                model.leaveProjectReview(
                    projectId = args.projectId,
                    rating = args.rating,
                    comment = args.comment,
                )
            }
            onSuccess {
                update { state -> state.copy(reviewProjectId = null) }
                simpleSnackbar(resRef(R.string.review_success))
            }
            onError { errorSnack(it) }
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
    @Immutable
    data class Details(
        val isVacancy: Boolean,
        val id: String
    ) : ActiveScreenResult

    data object Finish : ActiveScreenResult
}
