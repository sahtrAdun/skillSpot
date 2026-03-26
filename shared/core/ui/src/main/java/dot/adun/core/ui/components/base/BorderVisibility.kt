package dot.adun.core.ui.components.base 


enum class BorderVisibility {
    Always,
    Newer,
    WithCondition;

    fun visible(visible: Boolean): Boolean {
        return when (this) {
            Always -> true
            Newer -> false
            WithCondition -> visible
        }
    }
}
