package com.vavada.aso26.ui.fragment
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log

import com.google.gson.reflect.TypeToken
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.vavada.aso26.UiChangeInterface
import com.vavada.aso26.databinding.FragmentVerifyCodeBinding
import com.vavada.aso26.models.Action

import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo
import androidx.ads.identifier.AdvertisingIdInfo
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import com.vavada.aso26.ApiInterface
import com.vavada.aso26.Utils.KeyboardUtils
import com.vavada.aso26.models.ColoredString
import com.vavada.aso26.models.offers.OffersModel
import com.vavada.aso26.models.verify_phone
import com.vavada.aso26.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.thread
class Verify_Code (contextt: Context,uiChangeInterface: UiChangeInterface): Fragment() {
    var uiChangeInterface: UiChangeInterface
    var contextt: Context
    init {
        this.uiChangeInterface = uiChangeInterface
        this.contextt = contextt
    }
    override fun onStart() {
        super.onStart()
        binding.edCode.requestFocus()
         showKeyboard(binding.edCode)
    }
    fun showKeyboard(ettext: EditText) {
        ettext.requestFocus()
        ettext.postDelayed({
            if(isAdded){
                val keyboard: InputMethodManager? =
                    requireActivity().  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                keyboard!!.showSoftInput(ettext, 0)
            }

        }, 200)
    }
    private var _binding: FragmentVerifyCodeBinding? = null

    private fun setDataFromSharedPreferences( key:String,curProduct: Action) {
        val gson = Gson()
        val jsonCurProduct = gson.toJson(curProduct)
        val editor = prefs.edit()
        editor.putString(key, jsonCurProduct)
        editor.apply()
    }
    lateinit var sharedPreference : SharedPreferences
    lateinit var prefs: SharedPreferences
    private val binding get() = _binding!!
    lateinit var  phone: String
    var fullAccess :Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyCodeBinding.inflate(inflater, container,false)
        val view= binding.root
        sharedPreference   = requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        prefs= requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        phone = prefs.getString("code", "")+prefs.getString("phone", "")
        KeyboardUtils.addKeyboardToggleListener(requireActivity(), object :
            KeyboardUtils.SoftKeyboardToggleListener {
            override fun onToggleSoftKeyboard(isVisible: Boolean) {
                if(isVisible){
                    binding.mlVerifyCode.transitionToEnd()
                }
                else{
                    binding.mlVerifyCode.transitionToStart()
                }
            }
        })
        fullAccess = prefs.getBoolean("fullaccess", false)
        binding.mbGetAccess.text =prefs.getString("authgetaccess", "")
binding.tvRegisterAndGet.setColoredText(getDataFromSharedPreferences("nextcaption").title(phone, "#fff"))
binding.tvGet100Slots.setText(prefs.getString("subtitle",""))
        binding.tvChangeNumber.setText(prefs.getString("changenumber",""))
        binding.tvRecall.setText(getDataFromSharedPreferences("nextcaption").nextCaption())
       setCaption(getDataFromSharedPreferences("nextcaption"))
        binding.tvRecall.setOnClickListener({
     verifyPhoneForeword(phone, editor)
        })
binding.mbGetAccess.setOnClickListener({

    editor.putBoolean("accessoffers", true).apply()
 checkcode()
})
        return view
    }
    fun checkcode(){
        val apiInterface = ApiInterface. create().checkifvalid(binding.edCode.text.toString(),
            AppsFlyerLib.getInstance().getAppsFlyerUID(context), fetchAdvertisingId { object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }
            }},OneSignal.getDeviceState()?.userId ?: "none", prefs.getString("code", "")+prefs.getString("phone", "")!!
        )
        apiInterface.enqueue( object : Callback<verify_phone> {
            override fun onResponse(call: Call<verify_phone>, response: Response<verify_phone>) {
                if(response.body()!!.param_valid){
                    if(fullAccess){
                        getOffersData()

                    }
                }
                else{
                    Toast.makeText(requireContext(), prefs.getString("errorcode",""), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<verify_phone>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }
    fun changeui(){
        uiChangeInterface.show(FragmentOffers())
    }
    private fun setDataFromSharedPreferencesResponse(key:String, curProduct: OffersModel?) {
        val gson = Gson()
        val jsonCurProduct = gson.toJson(curProduct)
        val editor = prefs.edit()
        editor.putString(key, jsonCurProduct)
        editor.apply()
    }
    fun getOffersData(){
        val apiInterface = ApiInterface.create().listoffers(phone)
        apiInterface.enqueue( object : Callback<OffersModel> {
            override fun onResponse(call: Call<OffersModel>, response: Response<OffersModel>) {
                if(response.body() != null){
                    setDataFromSharedPreferencesResponse("response", response.body())
                    changeui()
                }
            }
            override fun onFailure(call: Call<OffersModel>, t: Throwable) {
            }
        })
    }
    private fun fetchAdvertisingId(callback: (String) -> Unit){
        thread(start = true) {
            var adInfo: AdvertisingIdInfo? = null
            try {
                @Suppress("BlockingMethodInNonBlockingContext")
                adInfo = getAdvertisingIdInfo(requireContext()).get()
            } catch (e: Exception) {
            }
            callback(adInfo?.id ?: "none")
        }
    }
    fun verifyPhoneForeword( phone:String, editor: SharedPreferences.Editor){
        val apiInterface = ApiInterface.create().Verify_PhoneForeward(phone)
        apiInterface.enqueue( object : Callback<Action> {
            override fun onResponse(call: Call<Action>, response: Response<Action>) {
                if(response.body() != null){
                    mainHandler.removeCallbacks(runnable)
                    val action:Action = response.body()!!
                    editor.putString("back",phone ).apply()
                editor.putString("actualtitle", action.ActualTitle.replace("%s", phone) ).apply()
                editor.putString("subtitle", action.Subtitle ).apply()
              editor.putString("nextactionpattern",action.NextActionPattern ).apply()
                    setDataFromSharedPreferences("nextcaption", action)
                    setCaption(action)
                    binding.tvRegisterAndGet.setColoredText(action.title(phone,"fff"))
                    binding.tvGet100Slots.setText(action.Subtitle)
                }
            }
            override fun onFailure(call: Call<Action>, t: Throwable) {
            }
        })
    }
    fun TextView.setColoredText(coloredString: ColoredString) {
        val spannable = SpannableString(coloredString.Text)
        for (span in coloredString.Colors) {
            val color = Color.parseColor(span.Color)
            spannable.setSpan(
                ForegroundColorSpan(color),
                span.From,
                span.To,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text = spannable
    }
    lateinit var  runnable: Runnable
    lateinit var mainHandler: Handler
    fun setCaption(caption:Action){
         mainHandler = Handler(Looper.getMainLooper())
        runnable= object :Runnable{
            override fun run() {
                    if(isAdded){
                        requireActivity().runOnUiThread {
                            binding.tvRecall.setText(caption.nextCaption())
                        }}
                mainHandler.postDelayed(this, 500)
            }
        }
        runnable.run()
    }
    private fun getDataFromSharedPreferences(key:String): Action {
        val gson = Gson()
        val productFromShared: Action
        val jsonPreferences = prefs.getString(key, "")
        val type = object : TypeToken<Action>() {}.type
        productFromShared = gson.fromJson(jsonPreferences, type)
        return productFromShared
    }
}