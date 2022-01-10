package com.vavada.aso26.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.vavada.aso26.interfaces.ApiInterface
import com.vavada.aso26.interfaces.UiChangeInterface
import com.vavada.aso26.databinding.FragmentSplashBinding
import com.vavada.aso26.models.ColoredString
import com.vavada.aso26.models.Config
import com.vavada.aso26.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashFragment (contextt: Context, uichange: UiChangeInterface): Fragment() {
    var uichange: UiChangeInterface
    var contextt: Context
    private var _binding: FragmentSplashBinding? = null
    lateinit var sharedPreference : SharedPreferences
    var accessOffers :Boolean = false
    lateinit var prefs: SharedPreferences
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container,false)
        val view= binding.root
        sharedPreference   = requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        prefs= requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        val editor= sharedPreference.edit()
        accessOffers = prefs.getBoolean("accessoffers", false)
if(!accessOffers){
       registerFragment(editor)}

    return view
    }
    fun registerFragment(editor:SharedPreferences.Editor){
        val list:List<ColoredString>
        list= ArrayList()

        val apiInterface = ApiInterface.create().getConfig()
        apiInterface.enqueue( object : Callback<Config> {
            override fun onResponse(call: Call<Config>?, response: Response<Config>?) {

                if(response?.body() != null)
                    if (prefs.getBoolean("fullaccess", true)) {
                        list.add(0, response.body()!!.Localization.AuthTitle)
                        editor.putBoolean("fullaccess", response.body()!!.FullAccess)
                        editor.putString("countries", response.body()!!.Countries)
                        editor.putString("authpolicyacceptance", response.body()!!.Localization.AuthPolicyAcceptance)
                        editor.putString("errorcode", response.body()!!.Localization.ErrorCode)
                        editor.putString("errorphone", response.body()!!.Localization.ErrorPhone)
                        editor.putString("errorserver", response.body()!!.Localization.ErrorServer)
                        editor.putString("authenterasguest", response.body()!!.Localization.AuthEnterAsGuest)
                        editor.putString("errorphone", response.body()!!.Localization.ErrorPhone)
                        editor.putString("authgetaccess", response.body()!!.Localization.AuthGetAccess)
                        editor.putString("ChangeNumberMessage",
                            response.body()!!.Localization.ChangeNumberMessage
                        ).apply()
                        editor.putString("ChangeNumberNo",
                            response.body()!!.Localization.ChangeNumberNo
                        ).apply()
                        editor.putString("ChangeNumberYes",
                            response.body()!!.Localization.ChangeNumberYes
                        ).apply()
                        setDataFromSharedPreferences("authpolicy",response.body()!!.Localization.AuthPolicy)
                        setDataFromSharedPreferences("authtitle",response.body()!!.Localization.AuthTitle)
                        setDataFromSharedPreferences("authsubtitle",response.body()!!.Localization.AuthSubtitle)
                        editor.apply()
                        uichange.show(RegisterFragment(requireContext(), requireActivity() as MainActivity))

                    }
                    else{
                        uichange.show(RegisterFragment(requireContext(), requireActivity() as MainActivity))
                    }

            }

            override fun onFailure(call: Call<Config>?, t: Throwable?) {
                if (prefs.getBoolean("fullaccess", false)) {

                    uichange.show(RegisterFragment(requireContext(), requireActivity() as MainActivity))
                }
                Log.d("sukaerror", t!!.localizedMessage)
            }
        })
    }
    private fun setDataFromSharedPreferences( key:String,curProduct: ColoredString) {
        val gson = Gson()
        val jsonCurProduct = gson.toJson(curProduct)

        val editor = prefs.edit()
        editor.putString(key, jsonCurProduct)
        editor.apply()
    }
    init {

        this.uichange = uichange
        this.contextt = contextt
    }
}