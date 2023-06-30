package com.lucasprioste.marvelshowcase.core


import java.math.BigInteger
import java.security.MessageDigest

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun isImageNotAvailable(uri: String): Boolean{
    return uri.contains("image_not_available")
}


sealed class AspectRatio(val type: String){
    object Landscape: AspectRatio(type = "landscape_xlarge")
    object Portrait: AspectRatio(type = "portrait_xlarge")
    object Standard: AspectRatio(type = "standard_xlarge")
}

sealed class OrderByCharacter(val type: String){
    object NameAscending: OrderByCharacter(type = "name")
    object NameDescending: OrderByCharacter(type = "-name")
    object ModifiedAscending: OrderByCharacter(type = "modified")
    object ModifiedDescending: OrderByCharacter(type = "-modified")
}

sealed class TypeDataRequest(val type: String){
    object Comics: TypeDataRequest(type = "comics")
    object Events: TypeDataRequest(type = "events")
    object Series: TypeDataRequest(type = "series")
    object Stories: TypeDataRequest(type = "stories")
}