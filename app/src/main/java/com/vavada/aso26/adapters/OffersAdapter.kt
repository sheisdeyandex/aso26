package com.vavada.aso26.adapters

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.button.MaterialButton
import com.vavada.aso26.MyDiffUtilCallback
import com.vavada.aso26.models.RecyclerViewItem
import com.vavada.aso26.models.offers.offerslist.Offers
import com.vavada.aso26.models.offers.offerslist.OffersListModel
import com.vavada.aso26.models.offers.testmodel
import java.util.*
import kotlin.collections.ArrayList
const val VIEW_TYPE_SECTION2 = 3
const val VIEW_TYPE_SECTION = 2
const val VIEW_TYPE_ITEM = 1


class OffersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
   private var data = ArrayList<RecyclerViewItem>()
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
    fun setData(newRating: List<RecyclerViewItem>) {
        val diffCallback = MyDiffUtilCallback(data, newRating)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)
        data.clear()
        data.addAll(newRating)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_SECTION2){
            return MineViewHolder(
                LayoutInflater.from(parent.context).inflate(com.vavada.aso26.R.layout.itemoffers1, parent, false)
            )
        }
        else   if (viewType == VIEW_TYPE_SECTION) {
            return SectionViewHolder(
                LayoutInflater.from(parent.context).inflate(com.vavada.aso26.R.layout.itemoffers, parent, false)
            )
        }

        return ContentViewHolder(
            LayoutInflater.from(parent.context).inflate(com.vavada.aso26.R.layout.itemoffersbutton, parent, false)
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
            val imageView =itemView.findViewById<ImageView>(com.vavada.aso26.R.id.iv_offerslist)
            val rloffers =itemView.findViewById<ConstraintLayout>(com.vavada.aso26.R.id.rl_offers)

            val caption: ImageView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_caption)
            val emoji:TextView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji)
            val captionwithcaption: ConstraintLayout = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_caption_with_caption)
            val emojiwithcaption:TextView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_with_caption)
            val emojiwithcaptiontext:TextView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_caption_text)
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
            val play: MaterialButton = itemView.findViewById(com.vavada.aso26.R.id.mb_play)
            Glide.with(imageView.context).asBitmap().load(item.offers.Background).into(object: CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                  imageView.background = BitmapDrawable(imageView.context.resources ,resource)
                }
            })
            Glide.with(imageView.context).load(item.offers.Foreground).into(imageView)

            val animDrawable = rloffers.background as AnimationDrawable
            animDrawable.setEnterFadeDuration(1)
            animDrawable.setExitFadeDuration(100)
            play.text = item.offers.ButtonText
            (play).setOnTouchListener(OnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {


                        animDrawable.start()

                        val view: MaterialButton = v as MaterialButton
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                        imageView.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                        animDrawable.stop()

                        val view: MaterialButton = v as MaterialButton
                        view.getBackground().clearColorFilter()
                        view.invalidate()
                        imageView.getBackground().clearColorFilter()
                        imageView.invalidate()
                    }
                }
                true
            })
            (imageView).setOnTouchListener(OnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {


                        animDrawable.start()

                        val view: ImageView = v as ImageView
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                        play.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                        animDrawable.stop()

                        val view: ImageView = v as ImageView
                        view.getBackground().clearColorFilter()
                        view.invalidate()
                        play.getBackground().clearColorFilter()
                        play.invalidate()
                    }
                }
                true
            })
        }
    }
    internal inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Offers) {
            val imageView =itemView.findViewById<ImageView>(com.vavada.aso26.R.id.iv_offerslist)
            val rloffers =itemView.findViewById<RelativeLayout>(com.vavada.aso26.R.id.rl_offers)
            val play: MaterialButton = itemView.findViewById(com.vavada.aso26.R.id.mb_play)
            val caption: ImageView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_caption)
            val emoji:TextView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji)
            val captionwithcaption: ConstraintLayout = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_caption_with_caption)
            val emojiwithcaption:TextView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_with_caption)
            val emojiwithcaptiontext:TextView = itemView.findViewById(com.vavada.aso26.R.id.iv_emoji_caption_text)
            if(!item.EmojiCaption!!.isEmpty()){
                emojiwithcaption.text  = item.Emoji
                captionwithcaption.visibility = View.VISIBLE
                emojiwithcaptiontext.text = item.EmojiCaption
            }
            else   if(item.EmojiCaption!!.isEmpty()&& !item.Emoji!!.isEmpty()){
                emoji.text  = item.Emoji
                emoji.visibility  = View.VISIBLE
                caption.visibility = View.VISIBLE
            }
            else{
                emoji.visibility = View.GONE
                caption.visibility = View.GONE
                captionwithcaption.visibility = View.GONE
            }

            val animDrawable = rloffers.background as AnimationDrawable
            animDrawable.setEnterFadeDuration(1)
            animDrawable.setExitFadeDuration(100)
            play.text = item.ButtonText
            (play).setOnTouchListener(OnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {


                        animDrawable.start()

                        val view: MaterialButton = v as MaterialButton
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                        imageView.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

        animDrawable.stop()

                        val view: MaterialButton = v as MaterialButton
                        view.getBackground().clearColorFilter()
                        view.invalidate()
                        imageView.getBackground().clearColorFilter()
                        imageView.invalidate()
                    }
                }
                true
            })
            (imageView).setOnTouchListener(OnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {


                        animDrawable.start()

                        val view: ImageView = v as ImageView
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                        play.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                        animDrawable.stop()

                        val view: ImageView = v as ImageView
                        view.getBackground().clearColorFilter()
                        view.invalidate()
                        play.getBackground().clearColorFilter()
                        play.invalidate()
                    }
                }
                true
            })
            play.setOnClickListener({  })
            Glide.with(itemView.findViewById<ImageView>(com.vavada.aso26.R.id.iv_offerslist) .context).asBitmap().load(item.Background).into(object: CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView.background = BitmapDrawable(imageView.context.resources ,resource)
                }
            })
            Glide.with(imageView.context).load(item.Foreground).into(imageView)
        }
    }
    internal inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: OffersListModel) {
            itemView.findViewById<TextView>(com.vavada.aso26.R.id.tv_button_text).text = item.Title
        }
    }
}