package ru.netology.yandexmapmarkers

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Reading API key from BuildConfig.
        // Do not forget to add your MAPKIT_API_KEY property to local.properties file.

        // В local.properties должен быть прописан именно тот ключ,
        // у которого в консоли Яндекса https://developer.tech.yandex.ru/services/
        // в списке допустимых идентификаторов приложений
        // присутствует идентификатор нашего приложения ru.netology.yandexmapmarkers
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)

    }
}
