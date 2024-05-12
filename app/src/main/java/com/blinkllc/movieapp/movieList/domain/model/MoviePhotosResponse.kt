package com.blinkllc.movieapp.movieList.domain.model

data class MoviePhotosResponse (
    var id:String,
    var owner:String,
    var secret:String,
    var server:String,
    var farm:Int,
    var title:String,
    var ispublic:Int,
    var isfriend:Int,
    var isfamily:Int
        )