package io.catter2.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import io.catter2.App
import io.catter2.cat_api.CacheTheCatAPI
import io.catter2.cat_api.RetrofitTheCatAPI
import io.catter2.cat_api.TheCatAPI
import java.security.InvalidParameterException

object AppKodein {
    lateinit var kodein: Kodein
        private set

    fun initialize(appContext: App, useCachedCatApi: Boolean) {
        try {
            kodein
            throw InvalidParameterException("AppKodein already initialized")
        } catch (e: UninitializedPropertyAccessException) {
            kodein = Kodein {
                bind<App>() with singleton { appContext }
                bind<Context>() with singleton { appContext }
                bind<TheCatAPI>() with singleton {
                    val service = RetrofitTheCatAPI()
                    when (useCachedCatApi) {
                        true -> CacheTheCatAPI(service)
                        else -> service
                    }
                }
            }
        }
    }

}

