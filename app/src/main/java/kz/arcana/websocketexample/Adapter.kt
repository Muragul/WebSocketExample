package kz.arcana.websocketexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.arcana.websocketexample.databinding.ItemBinding

class Adapter(
    private val list: ArrayList<String> = arrayListOf()
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    fun addMessage(msg: String) {
        list.add(msg)
        notifyItemChanged(list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(
        private val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvMessage.text = item
        }
    }

}