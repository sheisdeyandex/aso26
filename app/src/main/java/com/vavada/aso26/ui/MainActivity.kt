package com.vavada.aso26.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.vavada.aso26.interfaces.ApiInterface
import com.vavada.aso26.R
import com.vavada.aso26.interfaces.UiChangeInterface
import com.vavada.aso26.models.offers.OffersModel
import com.vavada.aso26.ui.fragment.FragmentOffers
import com.vavada.aso26.ui.fragment.SplashFragment
import com.vavada.aso26.ui.fragment.Verify_Code
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), UiChangeInterface {

    lateinit var prefs: SharedPreferences
    var accessOffers :Boolean = false
    lateinit var  phone: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs= getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        phone = prefs.getString("code", "")+prefs.getString("phone", "")
        accessOffers = prefs.getBoolean("accessoffers", false)
        if(accessOffers){

       getOffersData()
        }
        else{
            if(prefs.getString("back", null)!=null){
                show(Verify_Code(applicationContext, this))

            }
            else{

                show(SplashFragment(this, this))
            }
        }

hideSystemUI()
    }
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
    fun getOffersData(){
        show(SplashFragment(this, this))

        val apiInterface = ApiInterface.create().listoffers(phone)
        apiInterface.enqueue( object : Callback<OffersModel> {
            override fun onResponse(call: Call<OffersModel>, response: Response<OffersModel>) {
                if(response.body() != null){
setDataFromSharedPreferences("response", response.body())

                    show(FragmentOffers())
                }
            }
            override fun onFailure(call: Call<OffersModel>, t: Throwable) {
            }
        })
    }
    override fun show(fragment: Fragment) {
      supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,0,0).replace(R.id.container, fragment).commit()

    }
    private fun setDataFromSharedPreferences(key:String, curProduct: OffersModel?) {
        val gson = Gson()
        val jsonCurProduct = gson.toJson(curProduct)
        val editor = prefs.edit()
        editor.putString(key, jsonCurProduct)
        editor.apply()
    }
}