package dominicschumerth.c.breakingbadapp.ui.main.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominicschumerth.c.breakingbadapp.R
import dominicschumerth.c.breakingbadapp.model.Character
import dominicschumerth.c.breakingbadapp.ui.listener.AdapterOnClickItemListener

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    lateinit var item: Character
    private var mListener: AdapterOnClickItemListener<Character>? = null

    fun bind(item: Character, listener: AdapterOnClickItemListener<Character>?) {
        this.item = item
        mListener = listener

        itemView.findViewById<TextView>(R.id.tv_character_name).text = item.name
        Glide.with(itemView.context).load(item.image).into(itemView.findViewById(R.id.iv_character_image))

        itemView.setOnClickListener {
            mListener?.onItemClicked(adapterPosition, item)
        }
    }

}