package ru.netology.yandexmapmarkers

import android.os.Bundle
import android.util.Log
import android.widget.TextClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import ru.netology.yandexmapmarkers.dto.GeoPoint
import ru.netology.yandexmapmarkers.dto.Marker

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView // Для яндекс-карт

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY) // Можно (но плохо!) прописать конкретный ключ в виде строки
        MapKitFactory.initialize(this) //Инициализация библиотеки


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            Log.d("TRY", "Before find")
            val clock = findViewById<TextClock>(R.id.clock)
            clock?.let {
                Log.d("Prop", "timeZone=" + it.timeZone.toString())
                Log.d("Prop", "format12Hour=" + it.format12Hour.toString())
                Log.d("Prop", "format24Hour=" + it.format24Hour.toString())
                Log.d("Prop", "textSize=" + it.textSize.toString())
                //it.refreshTime()
                //Программно узнать текущую дату/время, отображаемые на компоненте,
                // можно через свойство textClock.text
                //It is possible to determine whether the system is currently
                // in 24-hour mode by calling is24HourModeEnabled().

            }
        } catch (e: Error) {
            Log.e("CATCH", "Cannot find clock props: " + e.toString())
        }

//////////////////////////////////////////////////

        val target = Marker(geoPoint = GeoPoint(57.02, 78.33))

//////////////////////////////////////////////////
        var stepNumber = 0
        try {
            // Яндекс-карты
            stepNumber++
            /*            MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY) // Можно (но плохо!) прописать конкретный ключ в виде строки
                        stepNumber++
                        MapKitFactory.initialize(this) //Инициализация библиотеки*/
            stepNumber++
//            setContentView(R.layout.activity_main)  // TODO Выше уже было -> Попробовать закомментить!
            stepNumber++
            mapView = findViewById(R.id.mapview)  // Размещаем во вью, доступный во всей активити

            /*Важно! С помощью вызова MapKitFactory.initialize(Context)
        загружаются все необходимые для MapKit нативные библиотеки.*/
        } catch (e: Exception) {
            Log.e("MapKit", "Cannot run step $stepNumber")
        }
    }

    override fun onStart() {
        /*Отправьте события onStart и onStop в MapKitFactory и MapView,
        переопределив методы Activity.onStart и Activity.onStop для Activity*/
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        /*Отправьте события onStart и onStop в MapKitFactory и MapView,
        переопределив методы Activity.onStart и Activity.onStop для Activity*/
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop() // именно при стопе вызываем родителя после прочих команд
    }
}
