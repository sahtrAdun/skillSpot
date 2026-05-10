package dot.adun.core.domain.annotations

@Target(AnnotationTarget.EXPRESSION, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@Suppress("UNCHECKED_CAST")
annotation class UncheckedCast
