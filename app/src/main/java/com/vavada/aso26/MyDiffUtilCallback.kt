package com.vavada.aso26

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vavada.aso26.models.RecyclerViewItem


class MyDiffUtilCallback(
    oldlist: List<RecyclerViewItem>,
    newlist: List<RecyclerViewItem>
) :
    DiffUtil.Callback() {
    var oldlist: List<RecyclerViewItem>
    var newlist: List<RecyclerViewItem>
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areItemsTheSame(olditempostion: Int, newitemPostion: Int): Boolean {
        return olditempostion == newitemPostion
    }

    override fun areContentsTheSame(olditempostion: Int, newitemPostion: Int): Boolean {
        return olditempostion == newitemPostion
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        this.oldlist = oldlist
        this.newlist = newlist
    }
}