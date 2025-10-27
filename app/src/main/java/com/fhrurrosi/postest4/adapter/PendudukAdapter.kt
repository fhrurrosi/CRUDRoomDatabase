package com.fhrurrosi.postest4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fhrurrosi.postest4.data.Penduduk
import com.fhrurrosi.postest4.databinding.ItemPendudukBinding

class PendudukAdapter(
    private val onEditClick: (Penduduk) -> Unit,
    private val onDeleteClick: (Penduduk) -> Unit
) : ListAdapter<Penduduk, PendudukAdapter.PendudukViewHolder>(PendudukComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendudukViewHolder {
        val binding = ItemPendudukBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PendudukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendudukViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class PendudukViewHolder(private val binding: ItemPendudukBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(penduduk: Penduduk) {
            binding.apply {
                tvNama.text = penduduk.nama
                tvNIK.text = "NIK: ${penduduk.nik}"
                tvAlamat.text = "RT ${penduduk.rt}/RW ${penduduk.rw}, ${penduduk.desa}, ${penduduk.kecamatan}, ${penduduk.kabupaten}"
                tvJenisKelamin.text = if (penduduk.jenisKelamin == "Laki-laki") "L" else "P"
                tvStatus.text = penduduk.statusPernikahan

                btnEdit.setOnClickListener {
                    onEditClick(penduduk)
                }

                btnDelete.setOnClickListener {
                    onDeleteClick(penduduk)
                }
            }
        }
    }

    class PendudukComparator : DiffUtil.ItemCallback<Penduduk>() {
        override fun areItemsTheSame(oldItem: Penduduk, newItem: Penduduk): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Penduduk, newItem: Penduduk): Boolean {
            return oldItem == newItem
        }
    }
}