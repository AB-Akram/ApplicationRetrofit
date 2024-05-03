package com.codabee.applicationretrofit.network

import com.codabee.applicationretrofit.model.Albums
import retrofit2.Response
import retrofit2.http.GET

interface AlbumService {
    @GET("albums")
    suspend fun getAlbums(): Response<Albums>
}