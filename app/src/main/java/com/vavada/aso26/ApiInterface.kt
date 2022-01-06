package com.vavada.aso26

import com.vavada.aso26.models.Action
import com.vavada.aso26.models.Config
import com.vavada.aso26.models.offers.OffersModel
import com.vavada.aso26.models.offers.offerslist.Offers
import com.vavada.aso26.models.offers.offerslist.OffersListModel
import com.vavada.aso26.models.verify_phone
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET("config")
    fun getConfig() : Call<Config>
    @GET("list")
    fun listoffers(@Query("param_phone") param_phone:String):Call< OffersModel>
    @FormUrlEncoded
    @POST("check_if_valid")
    fun checkifvalid(@Field("param_pin") param_pin:String, @Field("param_appsflyer") param_appsflyer:String, @Field("param_advertising_id") param_advertising_id: Unit, @Field("param_onesignal") param_onesignal:String, @Field("param_phone") param_phone:String ):Call<verify_phone>
    @GET("back")
    fun Verify_PhoneBack(@Query("param_phone") param_phone:String):Call<Action>
    @GET("foreward")
    fun Verify_PhoneForeward(@Query("param_phone") param_phone:String):Call<Action>
    companion object {
        var BASE_URL = "https://giucollaboration.us/"
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}