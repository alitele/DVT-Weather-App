package com.ciklum.weatherapp.network.api

import com.ciklum.weatherapp.features.home.model.Forecast
import com.ciklum.weatherapp.features.home.model.Weather
import retrofit2.Response
import retrofit2.http.*

interface Endpoints {

    // API to get current weather
    @GET("weather")
    suspend fun fetchCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("q") query:String,
        @Query("appid") api_key: String,
        @Query("units") units:String
    ): Response<Weather>

    // API to get forecast weather
    @GET("forecast")
    suspend fun fetchForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("q") query:String,
        @Query("appid") api_key: String,
        @Query("units") units:String
    ): Response<Forecast>

//
//    @GET("en/bedstack/api/legacy_stackconfig_get/")
//    suspend fun getBedList(): Response<List<Bed>>
//
//    @POST("en/shift/api/")
//    suspend fun confirmBedSelection(@Body createsShift: CreatesShift): Response<CreateShiftResponse>
//
//    @POST("en/shift/api/")
//    suspend fun updateBedSelection(@Body createsShift: CreatesShift): Response<UpdateShiftResponse>
//
//
//    @GET("/en/shift/api/")
//    suspend fun getCurrentShift(): Response<GetShiftResponse>
//
//    @POST("/en/device/{uuid}/heartbeat/")
//    suspend fun getHeartBeat(@Path("uuid") UUID: String): Response<GetHeartBeatResponse>
//
//    @POST("/o/revoke_token/")
//    @FormUrlEncoded
//    suspend fun logoutUser(
//        @Field("token") token: String,
//        @Field("client_id") client_id: String,
//        @Field("client_secret") client_secret: String
//    ): Response<ResponseBody>
//
//    @PUT("/en/bedstack/api/{bed_id}/componentconfig/")
//    suspend fun updateIndividualFilter(
//        @Body() requestFilterUpdate: Any,
//        @Path("bed_id") bed_id: String
//    ): Response<ResponseBody>
//
//    @PUT("/en/bedstack/api/{bed_id}/stackconfig/")
//    suspend fun updateStackFilter(
//        @Body() requestStackFilter: RequestStackFilter,
//        @Path("bed_id") bed_id: String
//    ): Response<ResponseBody>
//
//
//    @PUT("/en/bedstack/api/{bed_id}/stackconfig/")
//    suspend fun updateStackCoordinates(
//        @Body() requestCoordinates: RequestCoordinates,
//        @Path("bed_id") bed_id: String
//    ): Response<ResponseBody>
//
//
//    @PUT("/en/bedstack/api/notifications/seen/")
//    suspend fun setNotificationSeen(
//        @Body() requestNotificationSeen: RequestNotificationSeen
//    ): Response<ResponseBody>

}