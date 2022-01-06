package com.vavada.aso26.adapters

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.vavada.aso26.R
import com.vavada.aso26.models.RecyclerViewItem
import com.vavada.aso26.models.offers.offerslist.Offers
import com.vavada.aso26.models.offers.offerslist.OffersListModel
import com.vavada.aso26.models.offers.testmodel
import com.zhaoxing.view.sharpview.SharpImageView
import java.lang.Exception

const val VIEW_TYPE_SECTION = 1
const val VIEW_TYPE_SECTION2 = 3
const val VIEW_TYPE_ITEM = 2

class testadapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<RecyclerViewItem>()

    override fun getItemViewType(position: Int): Int {
        if (data[position] is testmodel) {
            return VIEW_TYPE_SECTION2
        }
      else  if (data[position] is Offers) {
            return VIEW_TYPE_SECTION
        }

        else  {
            return VIEW_TYPE_ITEM
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_SECTION2){
            return MineViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.itemoffers1, parent, false)
            )
        }
     else   if (viewType == VIEW_TYPE_SECTION) {
            return SectionViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.itemoffers, parent, false)
            )
        }

        return ContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.itemoffersbutton, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (holder is MineViewHolder && item is testmodel) {
            holder.bind(item)
        }
        if (holder is SectionViewHolder && item is Offers) {
            holder.bind(item)
        }
        if (holder is ContentViewHolder && item is OffersListModel) {
            holder.bind(item)
        }
    }
    internal inner class MineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: testmodel) {
            val caption:ImageView = itemView.findViewById(R.id.iv_emoji_caption)
            val emoji:TextView = itemView.findViewById(R.id.iv_emoji)
            val captionwithcaption:ConstraintLayout = itemView.findViewById(R.id.iv_emoji_caption_with_caption)
            val emojiwithcaption:TextView = itemView.findViewById(R.id.iv_emoji_with_caption)
            val emojiwithcaptiontext:TextView = itemView.findViewById(R.id.iv_emoji_caption_text)
            if(!item.offers.EmojiCaption!!.isEmpty()){
                emojiwithcaption.text  = item.offers.Emoji
                captionwithcaption.visibility = View.VISIBLE
                emojiwithcaptiontext.text = item.offers.EmojiCaption
            }
          else   if(item.offers.EmojiCaption!!.isEmpty()&& !item.offers.Emoji!!.isEmpty()){
                emoji.text  = item.offers.Emoji
                caption.visibility = View.VISIBLE
            }
            else{
                captionwithcaption.visibility = View.GONE
            }
            val play:MaterialButton = itemView.findViewById(R.id.mb_play)
            Picasso.get().load(item.offers.Foreground).into(itemView.findViewById<ImageView>(R.id.iv_offerslist) )
            Picasso.get().load(item.offers.Background).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    itemView.findViewById<ImageView>(R.id.iv_offerslist).background =
                        BitmapDrawable(itemView.findViewById<ImageView>(R.id.iv_offerslist).context.resources,bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }})
            play.text = item.offers.ButtonText
            play.setOnClickListener({  })
        }
    }
    internal inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Offers) {
            val play:MaterialButton = itemView.findViewById(R.id.mb_play)
            val caption:ImageView = itemView.findViewById(R.id.iv_emoji_caption)
            val emoji:TextView = itemView.findViewById(R.id.iv_emoji)
            val captionwithcaption:ImageView = itemView.findViewById(R.id.iv_emoji_caption_with_caption)
            val emojiwithcaption:TextView = itemView.findViewById(R.id.iv_emoji_with_caption)
            val emojiwithcaptiontext:TextView = itemView.findViewById(R.id.iv_emoji_caption_text)
            if(!item.EmojiCaption!!.isEmpty()){
                emojiwithcaption.text  = item.Emoji
                captionwithcaption.visibility = View.VISIBLE
                emojiwithcaptiontext.text = item.EmojiCaption
            }
            else   if(item.EmojiCaption!!.isEmpty()&& !item.Emoji!!.isEmpty()){
                emoji.text  = item.Emoji
                caption.visibility = View.VISIBLE
            }
            else{
                captionwithcaption.visibility = View.GONE
            }

            play.text = item.ButtonText
            play.setOnClickListener({  })
            Picasso.get().load(item.Foreground).into(itemView.findViewById<RoundedImageView>(R.id.iv_offerslist) )
            Picasso.get().load(item.Background).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    itemView.findViewById<ImageView>(R.id.iv_offerslist).background =
                        BitmapDrawable(itemView.findViewById<RoundedImageView>(R.id.iv_offerslist).context.resources,bitmap)
                }
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                }
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }})
        }
    }

    internal inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: OffersListModel) {
            itemView.findViewById<TextView>(R.id.tv_button_text).text = item.Title

        }
    }
}