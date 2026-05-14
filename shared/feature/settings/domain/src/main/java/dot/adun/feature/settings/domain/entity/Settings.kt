package dot.adun.feature.settings.domain.entity

sealed class Settings(val id: String) {
    object Theme : Settings(Id.Theme.value)

    enum class Id(val value: String) {
        Theme("settings_id_theme")
    }
}
