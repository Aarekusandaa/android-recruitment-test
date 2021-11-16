package dog.snow.androidrecruittest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.ui.model.ListItem
/*
class ListAdapter(private val onClick: (item: ListItem, position: Int, view: View) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<ListItem, ListAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView, onClick)
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder(
        itemView: View,
        private val onClick: (item: ListItem, position: Int, view: View) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem) = with(itemView) {
            val ivThumb: ImageView = findViewById(R.id.iv_thumb)
            val tvTitle: TextView = findViewById(R.id.tv_photo_title)
            val tvAlbumTitle: TextView = findViewById(R.id.tv_album_title)
            tvTitle.text = item.title
            tvAlbumTitle.text = item.albumTitle
            //display item.thumbnailUrl in ivThumb
            val url = item.thumbnailUrl
            Glide.with(ivThumb)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .fallback(R.drawable.ic_placeholder)
                .into(ivThumb)
            setOnClickListener { onClick(item, absoluteAdapterPosition, this) }
        }
    }

    fun setList(list: List<ListItem>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem == newItem
        }
    }
}
*/

class ListAdapter constructor(
    private val onClick: (item: ListItem, position: Int, view: View) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    //private lateinit var binding: FileItemBinding

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate binding
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false), onClick)
    }

    //set items' views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    //get number of items
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setList(list: List<ListItem>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    //handle single item's fields
    inner class ViewHolder(itemView: View,
                           private val onClick: (item: ListItem, position: Int, view: View) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private lateinit var listItem: ListItem

        fun bind(item: ListItem) = with(itemView) {
            val ivThumb: ImageView = findViewById(R.id.iv_thumb)
            val tvTitle: TextView = findViewById(R.id.tv_photo_title)
            val tvAlbumTitle: TextView = findViewById(R.id.tv_album_title)
            tvTitle.text = item.title
            tvAlbumTitle.text = item.albumTitle
            //display item.thumbnailUrl in ivThumb
            val url = item.thumbnailUrl
            Glide.with(ivThumb)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .fallback(R.drawable.ic_placeholder)
                .into(ivThumb)
            setOnClickListener {
                setOnClickListener { onClick(item, absoluteAdapterPosition, this) }
            }
        }
    }
    //interface for items clicks
    /*internal interface OnListFragmentInteractionListener {
        fun onListFragmentClickInteraction(file: ListItem, position: Int)
    }*/
}