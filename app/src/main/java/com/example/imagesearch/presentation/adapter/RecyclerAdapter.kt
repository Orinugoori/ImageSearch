package com.example.imagesearch.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.HolderDefaultBinding
import com.example.imagesearch.databinding.HolderRecyclerBinding
import com.example.imagesearch.presentation.ListItem
import com.example.imagesearch.presentation.viewmodel.LikeEvent
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

class RecyclerAdapter(private val likeEvent: LikeEvent) : ListAdapter<ListItem, RecyclerView.ViewHolder> (object  : DiffUtil.ItemCallback<ListItem>(){
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return when{
            oldItem is ListItem.ImageItem && newItem is ListItem.ImageItem -> oldItem.uid == newItem.uid
            oldItem is ListItem.VideoItem && newItem is ListItem.VideoItem -> oldItem.uid == newItem.uid
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}) {

    class ImageViewHolder(private val binding : HolderRecyclerBinding, private val likeEvent: LikeEvent):RecyclerView.ViewHolder(binding.root){
        fun bind(item : ListItem.ImageItem){
            with(item){
                Glide.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .into(binding.ivHolder)
                binding.tvHolderSite.text = siteName
                binding.tvHolderTime.text = formatDatetime(datetime)
                binding.tbHolder.isChecked= isLiked

                itemView.setOnClickListener {
                    likeEvent.like(item)
                }
            }


        }
    }

    class VideoViewHolder(private val binding : HolderRecyclerBinding, private val likeEvent: LikeEvent):RecyclerView.ViewHolder(binding.root){
        fun bind(item:ListItem.VideoItem){
            with(item){
                Glide.with(itemView.context)
                    .load(item.thumbnail)
                    .into(binding.ivHolder)
                binding.tvHolderSite.text = title
                binding.tvHolderTime.text = formatDatetime(datetime)
                binding.tbHolder.isChecked= isLiked

                itemView.setOnClickListener {
                    likeEvent.like(item)
                }
            }
        }
    }

    class DefaultHolder(binding: HolderDefaultBinding) :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val multiViewType = MultiViewEnum.entries.find { it.viewType == viewType }

        return when(multiViewType){
            MultiViewEnum.Image -> {
                ImageViewHolder(
                    HolderRecyclerBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ),parent,false
                    ),
                    likeEvent
                )
            }
            MultiViewEnum.Video -> {
                VideoViewHolder(
                    HolderRecyclerBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ),parent,false
                    ),
                    likeEvent
                )

            }
            else -> {
                DefaultHolder(
                    HolderDefaultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = getItem(position)){
            is ListItem.ImageItem -> {
                val imageViewHolder = holder as ImageViewHolder
                imageViewHolder.bind(getItem(position)as ListItem.ImageItem)
            }
            is ListItem.VideoItem -> {
                val videoViewHolder = holder as VideoViewHolder
                videoViewHolder.bind(getItem(position)as ListItem.VideoItem)
            }
            else -> {
                val defaultHolder = holder as DefaultHolder
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is ListItem.ImageItem -> MultiViewEnum.Image.viewType
            is ListItem.VideoItem -> MultiViewEnum.Video.viewType
            else -> MultiViewEnum.Unknown.viewType
        }
    }

}


fun formatDatetime(date: String): String {
    if(date.isEmpty()){
        return "N/A"
    }
    val originFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val offsetDateTime = OffsetDateTime.parse(date, originFormat)
    val newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return offsetDateTime.format(newFormat)
}