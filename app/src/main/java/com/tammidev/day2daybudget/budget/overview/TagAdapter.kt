package com.tammidev.day2daybudget.budget.overview

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tag.*

class TagAdapter(var tags: List<String>) : RecyclerView.Adapter<TagAdapter.TagVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagVH {
        return TagVH(LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false))
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagVH, position: Int) {
        holder.bind(tags[position])
    }

    fun tagsRefreshed(tags: List<String>?) {
        tags?.let {
            this.tags = tags
            notifyDataSetChanged()
            Log.d("Debug", "Tags: " + tags.toString())
        }
    }

    class TagVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        fun bind(tag: String) {
            tagView.text = tag
        }
    }
}