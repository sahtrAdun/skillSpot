package dot.adun.feature.authorized.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
data class ActiveProject(
    val vacancy: Vacancy,
    val executorResume: Resume
)
