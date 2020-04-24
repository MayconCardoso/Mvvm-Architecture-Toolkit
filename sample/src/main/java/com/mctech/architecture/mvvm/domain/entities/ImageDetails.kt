package com.mctech.architecture.mvvm.domain.entities

data class ImageDetails(
    val image : Image,
    val description : String,
    val bigImageUrlSource : String,
    val heightSize : Int,
    val widthSize : Int
)