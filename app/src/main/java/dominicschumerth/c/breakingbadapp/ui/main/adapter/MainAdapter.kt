package dominicschumerth.c.breakingbadapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dominicschumerth.c.breakingbadapp.R
import dominicschumerth.c.breakingbadapp.model.Character
import dominicschumerth.c.breakingbadapp.ui.listener.AdapterOnClickItemListener

class MainAdapter(list: ArrayList<Character>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mItems: List<Character> = list
    var mListener: AdapterOnClickItemListener<Character>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MainViewHolder).bind(mItems[position], mListener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun setListener(listener: AdapterOnClickItemListener<Character>) {
        this.mListener = listener
    }

    fun setCharacters(characters: List<Character>) {
        this.mItems = characters
        notifyDataSetChanged()
    }

}