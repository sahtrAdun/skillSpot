package dot.adun.core.domain.util

object Constants {
    object App {
        private const val KEY = "APP"

        object UI {
            private const val KEY = "${App.KEY}_UI"
            const val DARK_THEME = "${KEY}_DARK_THEME"
            const val LIGHT_THEME = "${KEY}_LIGHT_THEME"
            const val SYSTEM_THEME = "${KEY}_SYSTEM_THEME"
        }

        object SharedPrefs {
            private const val KEY = "${App.KEY}_PREFS"
            const val CURRENT_THEME = "${KEY}_CURRENT_THEME"
        }
    }
}