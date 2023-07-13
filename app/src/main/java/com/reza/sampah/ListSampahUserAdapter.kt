package com.reza.sampah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Trash
import com.reza.sampah.databinding.ItemRiwayatBinding

class ListSampahUserAdapter(private val onClickListener: RiwayatUserOnClickListener) : RecyclerView.Adapter<ListSampahUserAdapter.ListViewHolder>() {

    private val listsSampahUser  = arrayListOf<Trash>()

    fun setData(newList: List<Trash>){
        listsSampahUser.clear()
        listsSampahUser.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val view: ItemRiwayatBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: Trash){
            view.tvName.text = data.name
            view.alamat.text = data.address
            view.tvBerat.text = "Berat/Kg: ${data.weight}"
            view.tvJenissampah.text = data.category
            view.tvHarga.text = "Rp.${data.price}"
            view.sdhblmjmpt.setStatus(data.statusPickUp)
            view.delete.setOnClickListener { onClickListener.delete(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listsSampahUser[position])
    }

    override fun getItemCount(): Int  = listsSampahUser.size

    interface RiwayatUserOnClickListener {
        fun delete(trash: Trash)
    }

    private fun TextView.setStatus(statusPickUp: Boolean) {
        if (statusPickUp) {
            text = "Sudah Di Jemput"
            setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            text = "Sedang diperjalanan"
            setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }
}
