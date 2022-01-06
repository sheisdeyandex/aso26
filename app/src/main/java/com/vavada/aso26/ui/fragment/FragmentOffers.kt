package com.vavada.aso26.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vavada.aso26.ApiInterface
import com.vavada.aso26.CenterLayoutManager
import com.vavada.aso26.R
import com.vavada.aso26.databinding.FragmentOffersBinding
import com.vavada.aso26.databinding.FragmentVerifyCodeBinding
import com.vavada.aso26.models.Action
import com.vavada.aso26.models.LuckyFortuneCatModel
import com.vavada.aso26.models.offers.OffersModel
import com.vavada.aso26.models.offers.offerslist.Offers
import com.vavada.aso26.models.offers.offerslist.OffersListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.GridLayoutManager
import com.vavada.aso26.SpacesItemDecoration
import com.vavada.aso26.adapters.*
import com.vavada.aso26.models.RecyclerViewItem
import com.vavada.aso26.models.offers.testmodel
import java.lang.reflect.Array


class FragmentOffers : Fragment() {

    private var _binding: FragmentOffersBinding? = null
    private val binding get() = _binding!!
    lateinit var  phone: String
    lateinit var prefs: SharedPreferences
    lateinit var testModel :testmodel
    fun isOdd(`val`: Int): Boolean {
        return `val` and 0x01 != 0
    }
    var pulsepos= 0
    lateinit var offersListModel :OffersListModel
    var pos = ArrayList<Int>()
    private lateinit var offersAdapter: testadapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOffersBinding.inflate(inflater, container,false)
        val view= binding.root
        prefs= requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        phone = prefs.getString("code", "")+prefs.getString("phone", "")

        val dataSet = arrayListOf<RecyclerViewItem>(
        )
        val layoutManager = GridLayoutManager(requireContext(), 2)
        val apiInterface = ApiInterface.create().listoffers(phone)
        apiInterface.enqueue( object : Callback< OffersModel> {
            override fun onResponse(call: Call<OffersModel>, response: Response<OffersModel>) {
                if(response.body() != null){
                    for (i in  0..response.body()!!.array_lsit.size-1){
             offersListModel = response.body()!!.array_lsit.get(i)
dataSet.add(offersListModel)
                        if(isOdd(offersListModel.Offers.size)){
                        testModel = testmodel(offersListModel.Offers.get(0))

                       dataSet.add(testModel)
                        offersListModel.Offers.removeAt(0)}
dataSet.addAll(offersListModel.Offers)
                    }
                    layoutManager.setSpanSizeLookup(object :
                        GridLayoutManager.SpanSizeLookup() {

                        override fun getSpanSize(position: Int): Int {
                            //   if(isOdd(offersListModel.Offers.size)){



                            return when (offersAdapter.getItemViewType(position)) {

                                VIEW_TYPE_ITEM ->  2
                                VIEW_TYPE_SECTION -> 1
                                else -> -1
                            }
                        }

                        }



                        )
binding.rvOffers.addItemDecoration(SpacesItemDecoration(requireContext(), R.dimen._10sdp))
                    offersAdapter = testadapter()
                    offersAdapter.data = dataSet
                    binding.rvOffers.layoutManager= layoutManager
                    binding.rvOffers.adapter = offersAdapter
                }

            }
            override fun onFailure(call: Call<OffersModel>, t: Throwable) {
            }
        })



        return view
    }

}