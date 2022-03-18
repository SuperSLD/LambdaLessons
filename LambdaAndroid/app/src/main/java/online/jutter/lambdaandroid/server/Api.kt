package online.jutter.lambdaandroid.server

import retrofit2.http.GET

interface Api {

    @GET("api/hello/list")
    suspend fun getList() : List<String>
}