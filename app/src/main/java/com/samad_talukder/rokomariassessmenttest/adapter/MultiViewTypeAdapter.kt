package com.samad_talukder.rokomariassessmenttest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samad_talukder.rokomariassessmenttest.R
import com.samad_talukder.rokomariassessmenttest.model.response.BookModel
import com.samad_talukder.rokomariassessmenttest.utils.GlideUtils
import com.samad_talukder.rokomariassessmenttest.utils.ItemClickListener
import kotlinx.android.synthetic.main.item_explore_books.view.*
import kotlinx.android.synthetic.main.item_new_arrival_books.view.*
import kotlinx.android.synthetic.main.item_new_arrival_books.view.ivArrivalBookImage


class MultiViewTypeAdapter(private val itemClickListener: ItemClickListener, private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val NEW_ARRIVAL = 1

    }

    private val differCallBack = object : DiffUtil.ItemCallback<BookModel>() {
        override fun areItemsTheSame(
            oldItem: BookModel,
            newItem: BookModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BookModel,
            newItem: BookModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == NEW_ARRIVAL) {
            NewArrivalViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_new_arrival_books, parent, false)
            )
        } else {
            ExploreViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_explore_books, parent, false)
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userList = differ.currentList[position]

        if (holder is NewArrivalViewHolder) {
            holder.dataBind(userList)
        } else if (holder is ExploreViewHolder) {
            holder.dataBind(userList)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ExploreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun dataBind(results: BookModel) {

            itemView.tvExploreBookName.text = results.name_bn
            itemView.tvExploreBookAuthor.text = results.author_name_bn
            itemView.tvExploreBookPrice.text = results.price.toString()

            GlideUtils.showImage(itemView.context, results.image_path, itemView.ivExploreBookImage)

            itemView.setOnClickListener {
                itemClickListener.onItemClick(results)
            }
        }

    }

    inner class NewArrivalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun dataBind(results: BookModel) {

            GlideUtils.showImage(itemView.context, results.image_path, itemView.ivArrivalBookImage)

            itemView.setOnClickListener {
                itemClickListener.onItemClick(results)
            }
        }

    }
}