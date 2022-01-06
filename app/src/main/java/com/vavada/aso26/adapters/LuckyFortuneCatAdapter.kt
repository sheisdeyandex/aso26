package com.vavada.aso26.adapters
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vavada.aso26.databinding.ItemluckyfortunecatBinding
import com.vavada.aso26.models.LuckyFortuneCatModel

class LuckyFortuneCatAdapter (private val context: Context,private val images: ArrayList<LuckyFortuneCatModel>): RecyclerView.Adapter<LuckyFortuneCatAdapter.PhotoHolder>()  {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoHolder {
        val binding = ItemluckyfortunecatBinding.inflate(LayoutInflater.from(context),parent,false)
        return PhotoHolder(binding)
    }
    class PhotoHolder(private val binding: ItemluckyfortunecatBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(catsimage: LuckyFortuneCatModel){
            Picasso.get().load(catsimage.image).into(binding.ivCats)

        }
    }
    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val foodItem = images[position]
        holder.bind(foodItem)
   }

    override fun getItemCount(): Int {
       return images.size
    }
}