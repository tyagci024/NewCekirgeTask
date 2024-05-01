package com.example.newcekirge.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newcekirge.FavClickInterface
import com.example.newcekirge.adapter.UniversityAdapter
import com.example.newcekirge.databinding.FragmentFavoriteUniBinding
import com.example.newcekirge.databinding.ItemUniversityBinding
import com.example.newcekirge.model.University
import com.example.newcekirge.viewmodel.UniListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteUniFragment : Fragment(), FavClickInterface {
    private lateinit var binding: FragmentFavoriteUniBinding
    private lateinit var adapter: UniversityAdapter
    private val viewModel: UniListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUniBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsObserver()
    }

    private fun newsObserver() {
        viewModel.universityFavList.observe(viewLifecycleOwner) { newsList ->
            if (newsList.isNullOrEmpty()) {
                binding.textView.visibility = View.VISIBLE
                binding.recyclerViewFav.visibility = View.GONE
            } else {
                binding.textView.visibility = View.GONE
                binding.recyclerViewFav.visibility = View.VISIBLE
                adapter = UniversityAdapter(newsList, this)
                binding.recyclerViewFav.adapter = adapter
            }
        }
    }

    override fun favClicked(university: University, binding: ItemUniversityBinding) {
        viewModel.delete(university.name)
        Toast.makeText(
            requireContext(),
            "${university.name} favorilerden çıkarıldı",
            Toast.LENGTH_SHORT
        ).show()
    }


    override fun websiteClick(
        university: University,
        holder: UniversityAdapter.UniversityViewHolder
    ) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(university.website))
        startActivity(intent)
    }

    override fun dialPhone(university: University) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${university.phone}"))
        startActivity(intent)
    }
}