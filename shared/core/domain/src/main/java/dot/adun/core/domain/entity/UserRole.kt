package dot.adun.core.domain.entity

import javax.annotation.concurrent.Immutable

@Immutable
enum class UserRole {
    Freelancer,
    Customer,
    None;

    companion object {
        fun availableValues(): List<UserRole> = listOf(Freelancer, Customer)
    }
}