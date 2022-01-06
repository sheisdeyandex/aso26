package com.vavada.aso26.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.vavada.aso26.databinding.FragmentRegisterBinding
import com.vavada.aso26.models.ColoredString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import com.vavada.aso26.ApiInterface
import com.vavada.aso26.UiChangeInterface
import com.vavada.aso26.models.Action
import com.vavada.aso26.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment(contextt: Context,uiChangeInterface: UiChangeInterface) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var uiChangeInterface: UiChangeInterface
    var contextt: Context
    init {
        this.uiChangeInterface = uiChangeInterface
        this.contextt = contextt
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentRegisterBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    lateinit var sharedPreference : SharedPreferences
lateinit var registerandget:String
    lateinit var prefs: SharedPreferences
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container,false)
        val view= binding.root
        sharedPreference   = requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        prefs= requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        if(prefs.getString("back", null)!=null){
            uiChangeInterface.show(Verify_Code(requireContext(), requireActivity() as MainActivity))

        }

       binding.mbRegister.isClickable=false
binding.mbRegister.setOnClickListener({
    val phone = (binding.ccpCountryPicker.selectedCountryCodeWithPlus+ binding.mePhone.text.toString())
        .replace(" ", "")
        verifyPhoneBack(phone, editor)
})
        initCpp(editor)
   initTexts()
        return view
    }
    fun verifyPhoneBack( phone:String, editor: SharedPreferences.Editor){
        val apiInterface = ApiInterface.create().Verify_PhoneBack(phone)
        apiInterface.enqueue( object : Callback<Action> {

            override fun onResponse(call: Call<Action>, response: Response<Action>) {
                if(response.body() !=null){
                    editor.putString("back",phone ).apply()
                    editor.putString("code",binding.ccpCountryPicker.selectedCountryCodeWithPlus ).apply()
                    editor.putString("subtitle", response.body()?.Subtitle).apply()
                    editor.putString("nextactionpattern", response.body()?.NextActionPattern ).apply()
                    editor.putString("changenumber", response.body()?.ChangeNumber ).apply()
                    response.body()?.let { setDataFromSharedPreferences("nextcaption", it) }
                    uiChangeInterface.show(Verify_Code(requireContext(),  requireActivity() as MainActivity))
                }

                else{
                    Toast.makeText(requireContext(), "Errorphone", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Action>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun setDataFromSharedPreferences( key:String,curProduct: Action) {
        val gson = Gson()
        val jsonCurProduct = gson.toJson(curProduct)
        val editor = prefs.edit()
        editor.putString(key, jsonCurProduct)
        editor.apply()
    }
    private fun getDataFromSharedPreferences(key:String):ColoredString {
        val gson = Gson()
        var productFromShared: ColoredString
        val jsonPreferences = prefs.getString(key, "")
        val type = object : TypeToken<ColoredString>() {}.type
        productFromShared = gson.fromJson(jsonPreferences, type)
        return productFromShared
    }
    fun initTexts(){
        val playasguesttext:String = prefs.getString("authenterasguest","")!!
        val underlinedplayasguest = SpannableString(playasguesttext)
        underlinedplayasguest.setSpan(UnderlineSpan(), 0, playasguesttext.length, 0)
        binding.tvGet100Slots.setColoredText(getDataFromSharedPreferences("authsubtitle"))
        binding.tvRegisterAndGet.setColoredText(getDataFromSharedPreferences("authtitle"))
        binding.tvPrivacyPolicy.setColoredText(getDataFromSharedPreferences("authpolicy"))
        binding.tvPlayAsGuest.setText(underlinedplayasguest)
        binding.tvPrivacyPolicyAuthpolicy.setText(prefs.getString("authpolicyacceptance",""))
    }
    fun initCpp( editor:SharedPreferences.Editor){
        val valid = binding.ccpCountryPicker.isValidFullNumber

        binding.mePhone.setText(prefs.getString("phone", ""))
        binding.mePhone.addTextChangedListener {
            val phoneToSave = it.toString()
            editor.putString("phone",  phoneToSave).apply()

            if(valid){
                binding.mbRegister.isClickable = true
            }
            else{
           //     binding.mePhone.error = prefs.getString("errorphone","")
            }
        }

        if ( prefs.getString("countrycode", null)==null) { // если ранее код не сохранялся
            binding.ccpCountryPicker.detectSIMCountry(true)
            if (binding.ccpCountryPicker.selectedCountryNameCode == "US") {
                binding.ccpCountryPicker.setCountryForNameCode("RU")
            }
        } else {
            binding.ccpCountryPicker.setCountryForNameCode( prefs.getString("countrycode", null))
        }

        binding.ccpCountryPicker.setCustomMasterCountries(prefs.getString("countries","ru"))
        binding.ccpCountryPicker.setCcpDialogShowPhoneCode(true)
        binding.ccpCountryPicker.registerCarrierNumberEditText(binding.mePhone)
        binding.ccpCountryPicker.isSearchAllowed = false
        binding.ccpCountryPicker.setOnCountryChangeListener {
            val countryNameCode = binding.ccpCountryPicker.selectedCountryNameCode
            editor.putString("countrycode", countryNameCode).apply()
        }
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

}