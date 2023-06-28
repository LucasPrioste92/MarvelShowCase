package com.lucasprioste.marvelshowcase.core


import com.lucasprioste.marvelshowcase.core.QualityThumbnail.*
import java.math.BigInteger
import java.security.MessageDigest

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

sealed class AspectRatio(val type: String){
    data class Landscape(val quality: QualityThumbnail = XLarge): AspectRatio(type = "landscape_${quality.name}")
    data class Portrait(val quality: QualityThumbnail = XLarge): AspectRatio(type = "portrait_${quality.name}")
    data class Standard(val quality: QualityThumbnail = XLarge): AspectRatio(type = "standard_${quality.name}")
}

sealed class QualityThumbnail(val name: String){
    object Small: QualityThumbnail(name = "small")
    object Medium: QualityThumbnail(name = "medium")
    object Large: QualityThumbnail(name = "large")
    object XLarge: QualityThumbnail(name = "xlarge")
}

sealed class OrderByCharacter(val type: String){
    object NameAscending: OrderByCharacter(type = "name")
    object NameDescending: OrderByCharacter(type = "-name")
    object ModifiedAscending: OrderByCharacter(type = "modified")
    object ModifiedDescending: OrderByCharacter(type = "-modified")
}

enum class GenericItemType{
    COVER, INTERIOR
}