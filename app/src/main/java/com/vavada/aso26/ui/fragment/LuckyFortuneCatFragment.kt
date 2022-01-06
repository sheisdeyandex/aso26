package com.vavada.aso26.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vavada.aso26.CenterLayoutManager
import com.vavada.aso26.adapters.LuckyFortuneCatAdapter
import com.vavada.aso26.databinding.FragmentLuckyFortuneCatBinding
import com.vavada.aso26.models.LuckyFortuneCatModel
import androidx.recyclerview.widget.RecyclerView



class LuckyFortuneCatFragment : Fragment() {

    private var _binding: FragmentLuckyFortuneCatBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    lateinit var sharedPreference :SharedPreferences


    private val binding get() = _binding!!
    private var balance:Int = 35000
    private lateinit var catsmodel: ArrayList<LuckyFortuneCatModel>
    private lateinit var catsmodel2: ArrayList<LuckyFortuneCatModel>
    private lateinit var catsmodel3: ArrayList<LuckyFortuneCatModel>
    private lateinit var catsremovemodel: ArrayList<LuckyFortuneCatModel>
    private lateinit var catsremovemodel2: ArrayList<LuckyFortuneCatModel>
    private lateinit var catsremovemodel3: ArrayList<LuckyFortuneCatModel>
var bet:Int = 50
var win:Int = 0
    lateinit var prefs: SharedPreferences
    private lateinit var luckyFortuneCatAdapter: LuckyFortuneCatAdapter
    private lateinit var luckyFortuneCatAdapter2: LuckyFortuneCatAdapter
    private lateinit var luckyFortuneCatAdapter3: LuckyFortuneCatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLuckyFortuneCatBinding.inflate(inflater, container,false)
        val view= binding.root
     sharedPreference   = requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        prefs= requireActivity().getSharedPreferences("PREFERENCE_SLOTS", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
      //  balance =prefs.getInt("balance", 35000)
        binding.tvBalance.text = balance.toString()
        binding.tvBet.text= bet.toString()
        binding.clPlus.setOnClickListener(
            {
                if(bet<=1000){
                    bet+=50
                }
                else if(bet>1000){
                    Toast.makeText(requireContext(), "it is max bet", Toast.LENGTH_SHORT).show()
                }




        binding.tvBet.text = bet.toString()
        })
        binding.clMinus.setOnClickListener(
            {
if(bet>=100){
    bet-=50

}



                binding.tvBet.text = bet.toString()
            })
        catsmodel = ArrayList()
        catsmodel2 = ArrayList()
        catsmodel3 = ArrayList()
      addItems()
addItemsReelTwo()
        addItemsReelThree()
        binding.rvFirstReel.setLayoutManager(object : CenterLayoutManager(requireContext()) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                // force height of viewHolder here, this will override layout_height from xml
                lp.height = height / 3
                return true
            }
        })
        binding.rvSecondReel.setLayoutManager(object : CenterLayoutManager(requireContext(),
            VERTICAL,false) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                // force height of viewHolder here, this will override layout_height from xml
                lp.height = height / 3
                return true
            }
        })
        binding.rvThirdReel.setLayoutManager(object : CenterLayoutManager(requireContext()) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                // force height of viewHolder here, this will override layout_height from xml
                lp.height = height / 3
                return true
            }
        })
        binding.ivSpin.setOnClickListener({
            binding.ivSpin.isClickable = false
            countbalance()
            object:CountDownTimer(1500, 1000){
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {

                    binding.ivSpin.isClickable=true
                 countwin()
                }

            }.start()
        })
        luckyFortuneCatAdapter = LuckyFortuneCatAdapter(requireContext(),catsmodel )
        luckyFortuneCatAdapter2 = LuckyFortuneCatAdapter(requireContext(),catsmodel2 )
        luckyFortuneCatAdapter3 = LuckyFortuneCatAdapter(requireContext(),catsmodel3 )
        binding.rvFirstReel.adapter = luckyFortuneCatAdapter
        binding.rvSecondReel.adapter = luckyFortuneCatAdapter2
        binding.rvThirdReel.adapter = luckyFortuneCatAdapter3

        return view
    }
    fun countbalance(){
        if(bet<=1000&&balance>=bet){
            balance= balance-bet
            binding.tvBalance.text= balance.toString()
            spin()
        }
        else if(bet>1000){
            Toast.makeText(requireContext(), "it is max bet", Toast.LENGTH_SHORT).show()
        }
      else{
            Toast.makeText(requireContext(), "not enough funds", Toast.LENGTH_SHORT).show()

        }
    }
    fun countwin(){

        binding.rvFirstReel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = getCurrentItem()
                    if(catsmodel.get(position+1).image==catsmodel2.get(position+1).image&&catsmodel2.get(position+1).image==catsmodel3.get(position+1).image){

                        win= bet*30+win
                        balance+=win
                        binding.tvWin.text = win.toString()
                        binding.tvBalance.text = balance.toString()
                    }
                   else if(catsmodel.get(position+2).image==catsmodel2.get(position+2).image&&catsmodel2.get(position+2).image==catsmodel3.get(position+2).image){

                        win= bet*30+win
                        balance+=win
                        binding.tvWin.text = win.toString()
                        binding.tvBalance.text = balance.toString()

                    }
                  else  if(catsmodel.get(position+3).image==catsmodel2.get(position+3).image&&catsmodel2.get(position+3).image==catsmodel3.get(position+3).image){

                        win= bet*30+win
                        balance+=win
                        binding.tvWin.text = win.toString()
                        binding.tvBalance.text = balance.toString()

                    }
                 else   if(catsmodel.get(position+1).image==catsmodel2.get(position+2).image&&catsmodel2.get(position+2).image==catsmodel3.get(position+3).image){
                        win= bet*30+win
                        balance+=win
                        binding.tvWin.text = win.toString()
                        binding.tvBalance.text = balance.toString()
                    }
                    else{

                        binding.tvWin.text= win.toString()
                    }
                }
            }
        })
    }
    private fun getCurrentItem(): Int {
        return ( binding.rvFirstReel.getLayoutManager() as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }

    fun spin(){
        win=0
        binding.rvFirstReel.smoothScrollToPosition(20)
        binding.rvSecondReel.smoothScrollToPosition(20)
        binding.rvThirdReel.smoothScrollToPosition(20)
        catsmodel.clear()
        catsmodel2.clear()
        catsmodel3.clear()
       addItems()
        addItemsReelTwo()
        addItemsReelThree()
        luckyFortuneCatAdapter.notifyItemRangeRemoved(2, 97)
        luckyFortuneCatAdapter2.notifyItemRangeRemoved(2, 97)
        luckyFortuneCatAdapter3.notifyItemRangeRemoved(2, 97)

    }
    fun addItems(){
        for (i in 1..100) {
            val rnds9 = (1..9).random()
            val rnds1 = (1..9).random()
            val rnds2 = (1..9).random()
            val rnds3 = (1..9).random()
            val rnds4 = (1..9).random()
            val rnds5 = (1..9).random()
            val rnds6 = (1..9).random()
            val rnds7 = (1..9).random()
            val rnds8 = (1..9).random()
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds9)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds1)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds2)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds3)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds4)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds5)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds6)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds7)))
            catsmodel.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds8)))
        }
    }  fun addItemsReelTwo(){
        for (i in 1..100) {
            val rnds9 = (1..9).random()
            val rnds1 = (1..9).random()
            val rnds2 = (1..9).random()
            val rnds3 = (1..9).random()
            val rnds4 = (1..9).random()
            val rnds5 = (1..9).random()
            val rnds6 = (1..9).random()
            val rnds7 = (1..9).random()
            val rnds8 = (1..9).random()
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds9)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds1)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds2)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds3)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds4)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds5)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds6)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds7)))
            catsmodel2.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds8)))
        }
    }  fun addItemsReelThree(){
        for (i in 1..100) {
            val rnds9 = (1..9).random()
            val rnds1 = (1..9).random()
            val rnds2 = (1..9).random()
            val rnds3 = (1..9).random()
            val rnds4 = (1..9).random()
            val rnds5 = (1..9).random()
            val rnds6 = (1..9).random()
            val rnds7 = (1..9).random()
            val rnds8 = (1..9).random()
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds9)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds1)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds2)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds3)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds4)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds5)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds6)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds7)))
            catsmodel3.add(LuckyFortuneCatModel( Uri.parse("android.resource://com.vavada.aso26/drawable/symbol_"+rnds8)))
        }
    }
}