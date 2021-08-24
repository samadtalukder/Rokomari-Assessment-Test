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
import kotlinx.android.synthetic.main.activity_book_details.view.*
import kotlinx.android.synthetic.main.item_new_arrival_books.view.*


class NewArrivalAdapter : RecyclerView.Adapter<NewArrivalAdapter.NewArrivalViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArrivalViewHolder {
        return NewArrivalViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_new_arrival_books, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewArrivalViewHolder, position: Int) {
        val userList = differ.currentList[position]
        holder.dataBind(userList)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((BookModel) -> Unit)? = null

    fun setOnClickListener(listener: (BookModel) -> Unit) {
        onItemClickListener = listener
    }

    inner class NewArrivalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun dataBind(results: BookModel) {

            GlideUtils.showImage(itemView.context,results.image_path,itemView.ivArrivalBookImage)

            itemView.setOnClickListener {
                onItemClickListener?.let { it(results) }
            }
        }




    }
}