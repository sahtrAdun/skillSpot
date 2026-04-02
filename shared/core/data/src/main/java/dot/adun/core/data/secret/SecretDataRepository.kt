package dot.adun.core.data.secret

import android.content.SharedPreferences
import dot.adun.core.domain.secret.SecretRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.prefs.Preferences

@Singleton
class SecretDataRepository @Inject constructor(
    //private val appPreferences: DataStore<Preferences>
) : SecretRepository {
    override fun test() {
        println("test")
    }
}
