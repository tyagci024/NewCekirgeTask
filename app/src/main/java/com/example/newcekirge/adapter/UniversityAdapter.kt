package com.example.newcekirge.adapter

import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newcekirge.FavClickInterface
import com.example.newcekirge.R
import com.example.newcekirge.databinding.ItemUniversityBinding
import com.example.newcekirge.model.University
import com.example.newcekirge.room.UniDao
import com.example.newcekirge.view.UniListFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UniversityAdapter(
    private val universities: List<University>,
    private val favClickInterface: FavClickInterface
) : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {


    inner class UniversityViewHolder(val binding: ItemUniversityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.textViewUniversityPhone.setOnClickListener {
                val university = universities[adapterPosition]
                favClickInterface.dialPhone(university)
            }
            binding.imageViewFavStar.setOnClickListener {
                val university = universities[adapterPosition]
                favClickInterface.favClicked(university, binding)
            }
        }

        fun bind(university: University) {
            binding.apply {

                textViewUniversityName.text = university.name

                val isExpanded = expandedMap[adapterPosition] ?: false
                layoutUniversityDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
                imageViewToggleDetails.setImageResource(if (isExpanded) R.drawable.minus else R.drawable.plus)

                root.setOnClickListener {
                    expandedMap[adapterPosition] = !isExpanded
                    notifyItemChanged(adapterPosition)
                }

                imageViewToggleDetails.setOnClickListener {
                    expandedMap[adapterPosition] = !isExpanded
                    notifyItemChanged(adapterPosition)
                }



                if (university.isFav) {
                    imageViewFavStar.setImageResource(R.drawable.enabled_fav_star)
                } else {
                    imageViewFavStar.setImageResource(R.drawable.star)
                }
                val anyDetailsNotEmpty = university.adress=="-" &&
                        university.email=="-" &&
                        university.fax=="-" &&
                        university.phone=="-"&&
                        university.rector=="-" &&
                        university.website=="-"

                imageViewToggleDetails.visibility = if (!anyDetailsNotEmpty) View.VISIBLE else View.GONE

                textViewUniversityAddress.text = binding.root.context.resources.getString(R.string.item_layout_adress, university.adress)
                textViewUniversityEmail.text = binding.root.context.resources.getString(R.string.item_layout_email, university.email)
                textViewUniversityFax.text = binding.root.context.resources.getString(R.string.item_layout_fax, university.fax)
                textViewUniversityPhone.text = binding.root.context.resources.getString(R.string.item_layout_phone, university.phone)
                textViewUniversityRector.text = binding.root.context.resources.getString(R.string.item_layout_rector, university.rector)
                textViewUniversityWebsite.text = binding.root.context.resources.getString(R.string.item_layout_web, university.website)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val binding =
            ItemUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        holder.bind(universities[position])

        holder.binding.textViewUniversityWebsite.setOnClickListener {
            favClickInterface.websiteClick(universities[position],holder)
        }
    }

    override fun getItemCount(): Int {
        return universities.size
    }

    companion object {
        private val expandedMap = HashMap<Int, Boolean>()
    }

}