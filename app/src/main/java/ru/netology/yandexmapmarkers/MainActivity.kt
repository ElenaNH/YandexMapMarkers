package ru.netology.yandexmapmarkers

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MainActivity : AppCompatActivity() {

    //private lateinit var mapView: MapView // Для яндекс-карт
    private val mapView by lazy { findViewById<MapView>(R.id.mapview) } // Для яндекс-карт

    // Слушатель нажатия на указанную метку
    private val placemarkTapListener = MapObjectTapListener { _, point ->
        Toast.makeText(
            this@MainActivity,
            "Tapped the point (${point.longitude}, ${point.latitude})",
            Toast.LENGTH_SHORT
        ).show()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY) // Можно (но плохо!) прописать конкретный ключ в виде строки
        MapKitFactory.initialize(this) //Инициализация библиотеки

        // Карта появляется тут (она встроена в activity_main)
        setContentView(R.layout.activity_main)

        //mapView = findViewById(R.id.mapview)  // Если бы было lateinit, то так рассчитываем

        // Тут мы можем что-то сделать с этой картой
        // (если by lazy, то прежде выполнится лямбда для расчета mapView)
        mapView?.also {
            Log.d("mapView " + it.tag.toString(), it.toString() )

            //it.map.move(
            it.mapWindow.map.move(
                CameraPosition(
                    Point(55.751225, 37.629540),
                    /* zoom = */ 17.0f,
                    /* azimuth = */ 150.0f,
                    /* tilt = */ 30.0f
                )
            )

        }

        // Добавление метки с нужной иконкой imageProvider
        val imageProvider = ImageProvider.fromResource(this, R.drawable.ic_baseline_maps_ugc_24)
        //val placemark = mapView.map.mapObjects.addPlacemark().apply {
        val placemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(59.935493, 30.327392)
            setIcon(imageProvider)
        }

        // Добавление определенного выше слушателя (теперь он начнет слушать)
        placemark.addTapListener(placemarkTapListener)

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
