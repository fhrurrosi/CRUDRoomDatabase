package com.fhrurrosi.postest4

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fhrurrosi.postest4.adapter.PendudukAdapter
import com.fhrurrosi.postest4.data.Penduduk
import com.fhrurrosi.postest4.databinding.ActivityMainBinding
import com.fhrurrosi.postest4.viewmodel.PendudukViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PendudukViewModel by viewModels()
    private lateinit var adapter: PendudukAdapter
    private var editingPenduduk: Penduduk? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupSpinner() {
        val statusArray = resources.getStringArray(R.array.status_pernikahan)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStatus.adapter = spinnerAdapter
    }

    private fun setupRecyclerView() {
        adapter = PendudukAdapter(
            onEditClick = { penduduk ->
                editPenduduk(penduduk)
            },
            onDeleteClick = { penduduk ->
                showDeleteConfirmation(penduduk)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupObservers() {
        viewModel.allPenduduk.observe(this) { pendudukList ->
            pendudukList?.let {
                adapter.submitList(it)

                if (it.isEmpty()) {
                    binding.emptyStateCard.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.emptyStateCard.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSimpan.setOnClickListener {
            if (validateInput()) {
                savePenduduk()
            }
        }

        binding.btnReset.setOnClickListener {
            resetForm()
            Toast.makeText(this, "Form direset", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (binding.etNama.text.isNullOrBlank()) {
            binding.etNama.error = "Nama tidak boleh kosong"
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        val nik = binding.etNIK.text.toString()
        when {
            nik.isBlank() -> {
                binding.etNIK.error = "NIK tidak boleh kosong"
                Toast.makeText(this, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show()
                isValid = false
            }
            nik.length != 16 -> {
                binding.etNIK.error = "NIK harus 16 digit"
                Toast.makeText(this, "NIK harus 16 digit", Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }

        if (binding.etKecamatan.text.isNullOrBlank()) {
            binding.etKecamatan.error = "Kecamatan tidak boleh kosong"
            Toast.makeText(this, "Kecamatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (binding.etKabupaten.text.isNullOrBlank()) {
            binding.etKabupaten.error = "Kabupaten tidak boleh kosong"
            Toast.makeText(this, "Kabupaten tidak boleh kosong", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (binding.etDesa.text.isNullOrBlank()) {
            binding.etDesa.error = "Desa tidak boleh kosong"
            Toast.makeText(this, "Desa tidak boleh kosong", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (binding.etRT.text.isNullOrBlank()) {
            binding.etRT.error = "RT tidak boleh kosong"
            Toast.makeText(this, "RT tidak boleh kosong", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (binding.etRW.text.isNullOrBlank()) {
            binding.etRW.error = "RW tidak boleh kosong"
            Toast.makeText(this, "RW tidak boleh kosong", Toast.LENGTH_SHORT).show()
            isValid = false
        }


        if (binding.rgJenisKelamin.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih jenis kelamin terlebih dahulu", Toast.LENGTH_SHORT).show()
            isValid = false
        }


        if (binding.spinnerStatus.selectedItemPosition == 0) {
            Toast.makeText(this, "Pilih status pernikahan terlebih dahulu", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun savePenduduk() {
        val nama = binding.etNama.text.toString().trim()
        val nik = binding.etNIK.text.toString().trim()
        val kecamatan = binding.etKecamatan.text.toString().trim()
        val kabupaten = binding.etKabupaten.text.toString().trim()
        val desa = binding.etDesa.text.toString().trim()
        val rt = binding.etRT.text.toString().trim()
        val rw = binding.etRW.text.toString().trim()

        val jenisKelamin = when (binding.rgJenisKelamin.checkedRadioButtonId) {
            R.id.rbLaki -> "Laki-laki"
            R.id.rbPerempuan -> "Perempuan"
            else -> ""
        }

        val statusPernikahan = binding.spinnerStatus.selectedItem.toString()

        if (editingPenduduk != null) {
            val updatedPenduduk = editingPenduduk!!.copy(
                nama = nama,
                nik = nik,
                kecamatan = kecamatan,
                kabupaten = kabupaten,
                desa = desa,
                rt = rt,
                rw = rw,
                jenisKelamin = jenisKelamin,
                statusPernikahan = statusPernikahan
            )
            viewModel.update(updatedPenduduk)
            Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
            editingPenduduk = null
            binding.btnSimpan.text = "Simpan"
        } else {
            val penduduk = Penduduk(
                nama = nama,
                nik = nik,
                kecamatan = kecamatan,
                kabupaten = kabupaten,
                desa = desa,
                rt = rt,
                rw = rw,
                jenisKelamin = jenisKelamin,
                statusPernikahan = statusPernikahan
            )
            viewModel.insert(penduduk)
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
        }

        resetForm()
    }

    private fun editPenduduk(penduduk: Penduduk) {
        editingPenduduk = penduduk

        binding.apply {
            etNama.setText(penduduk.nama)
            etNIK.setText(penduduk.nik)
            etKecamatan.setText(penduduk.kecamatan)
            etKabupaten.setText(penduduk.kabupaten)
            etDesa.setText(penduduk.desa)
            etRT.setText(penduduk.rt)
            etRW.setText(penduduk.rw)

            when (penduduk.jenisKelamin) {
                "Laki-laki" -> rbLaki.isChecked = true
                "Perempuan" -> rbPerempuan.isChecked = true
            }

            val statusArray = resources.getStringArray(R.array.status_pernikahan)
            val position = statusArray.indexOf(penduduk.statusPernikahan)
            spinnerStatus.setSelection(if (position >= 0) position else 0)

            btnSimpan.text = "Update"
        }

        binding.root.parent?.requestChildFocus(binding.root, binding.root)

        Toast.makeText(this, "Mode edit: ${penduduk.nama}", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteConfirmation(penduduk: Penduduk) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus data ${penduduk.nama}?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.delete(penduduk)
                Toast.makeText(this, "Data ${penduduk.nama} berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun resetForm() {
        binding.apply {
            etNama.text?.clear()
            etNIK.text?.clear()
            etKecamatan.text?.clear()
            etKabupaten.text?.clear()
            etDesa.text?.clear()
            etRT.text?.clear()
            etRW.text?.clear()
            rgJenisKelamin.clearCheck()
            spinnerStatus.setSelection(0)

            etNama.error = null
            etNIK.error = null
            etKecamatan.error = null
            etKabupaten.error = null
            etDesa.error = null
            etRT.error = null
            etRW.error = null
        }

        editingPenduduk = null
        binding.btnSimpan.text = "Simpan"
    }
}