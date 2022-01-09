package com.vavada.aso26.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.vavada.aso26.R
import com.vavada.aso26.SpacesItemDecoration
import com.vavada.aso26.adapters.*
import com.vavada.aso26.databinding.FragmentOffersBinding
import com.vavada.aso26.models.ColoredString
import com.vavada.aso26.models.RecyclerViewItem
import com.vavada.aso26.models.offers.OffersModel
import com.vavada.aso26.models.offers.offerslist.OffersListModel
import com.vavada.aso26.models.offers.testmodel
import com.google.gson.reflect.TypeToken

class FragmentOffers : Fragment() {
    private var _binding: FragmentOffersBinding? = null
    private val binding get() = _binding!!
    lateinit var  phone: String
    lateinit var prefs: SharedPreferences
    lateinit var testModel :testmodel
    fun isOdd(`val`: Int): Boolean {
        return `val` and 0x01 != 0
    }
    val dataSet = arrayListOf<RecyclerViewItem>()
    lateinit var offersListModel :OffersListModel
    lateinit var layoutManager:GridLayoutManager

    private lateinit var offersAdapter: OffersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOffersBinding.inflate(inflater, container,false)
        val view= binding.root
        prefs= requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        phone = prefs.getString("code", "")+prefs.getString("phone", "")
       layoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvOffers.addItemDecoration(SpacesItemDecoration(requireContext(), R.dimen._10sdp))
        offersAdapter = OffersAdapter()
        binding.rvOffers.layoutManager= layoutManager
        binding.rvOffers.adapter = offersAdapter
        initRecycler()
        binding.swlOffers.setOnRefreshListener {
            initRecycler()
            offersAdapter.notifyItemRangeChanged(0, dataSet.size)
        }


        return view
    }
    private fun getDataFromSharedPreferences(key:String): OffersModel? {
        val gson = Gson()
        var productFromShared: OffersModel?
        val jsonPreferences = prefs.getString(key, "")
        val type = object : TypeToken<OffersModel?>() {}.type
        productFromShared = gson.fromJson(jsonPreferences, type)
        return productFromShared
    }
    fun initlayoutManagerSpan(){
        layoutManager.setSpanSizeLookup(object :
            GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (offersAdapter.getItemViewType(position)) {
                    VIEW_TYPE_SECTION2 -> 2
                    VIEW_TYPE_ITEM ->  2
                    VIEW_TYPE_SECTION -> 1
                    else -> -1
                }
            }
        }
        )
    }
    fun getArraylistModel(response: OffersModel?){
        dataSet.clear()
        for (i in  0..response!!.array_lsit.size-1){
            offersListModel = response.array_lsit.get(i)
            dataSet.add(offersListModel)
            if(isOdd(offersListModel.Offers.size)){
                testModel = testmodel(offersListModel.Offers.get(0))

                dataSet.add(testModel)
                offersListModel.Offers.removeAt(0)}
            dataSet.addAll(offersListModel.Offers)
        }
    }
    fun initRecyclerAndAdapter(){

        offersAdapter.setData(dataSet)
    }

    fun initRecycler(){
        val response:OffersModel? = getDataFromSharedPreferences("response")
binding.swlOffers.isRefreshing = false
                    getArraylistModel(response)
                    initlayoutManagerSpan()
                    initRecyclerAndAdapter()


}}