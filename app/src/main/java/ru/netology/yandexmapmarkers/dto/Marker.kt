package ru.netology.yandexmapmarkers.dto

data class GeoPoint (
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)

data class Marker (
    val id: Int = 0,
    val geoPoint: GeoPoint = GeoPoint(),
    val title: String = "GeoPoint(${geoPoint.latitude},${geoPoint.longitude})",
    val anyAdditionalData: Any? = null,
)
