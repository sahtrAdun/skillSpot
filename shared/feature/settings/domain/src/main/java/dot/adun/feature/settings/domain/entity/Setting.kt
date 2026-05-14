package dot.adun.feature.settings.domain.entity

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import dot.adun.core.domain.entity.TextRef
import javax.annotation.concurrent.Immutable

@Immutable
sealed class Setting(
    open val id: Id,
    open val title: TextRef,
    open val description: TextRef? = null,
) {
    @JvmInline
    value class Id(val value: String)

    @Immutable
    data class Option(
        val id: Int,
        val value: String,
        val label: TextRef,
    )

    abstract fun key(): Preferences.Key<*>

    @Immutable
    data class Toggle(
        override val id: Id,
        override val title: TextRef,
        override val description: TextRef? = null,
        val value: Boolean,
    ) : Setting(id, title, description) {
        override fun key() = booleanPreferencesKey(id.value)
    }

    @Immutable
    data class Selector(
        override val id: Id,
        override val title: TextRef,
        override val description: TextRef? = null,
        val value: Int,
        val options: List<Option>,
    ) : Setting(id, title, description) {
        override fun key() = intPreferencesKey(id.value)

        val selectedOption: Option?
            get() = options.firstOrNull { it.id == value }?: options.firstOrNull()
    }
}
