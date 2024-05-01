package com.example.newcekirge.view

import CityAdapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newcekirge.FavClickInterface
import com.example.newcekirge.R
import com.example.newcekirge.adapter.UniversityAdapter
import com.example.newcekirge.databinding.FragmentUniListBinding
import com.example.newcekirge.databinding.ItemUniversityBinding
import com.example.newcekirge.model.University
import com.example.newcekirge.repository.Repository
import com.example.newcekirge.room.UniDao
import com.example.newcekirge.room.UniDatabase
import com.example.newcekirge.viewmodel.UniListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class UniListFragment : Fragment(), FavClickInterface {
    private lateinit var binding: FragmentUniListBinding
    private val viewModel: UniListViewModel by viewModels()
    private var recyclerState: Parcelable? = null
    private var currentPage=1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUniListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCities.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CityAdapter(emptyList(), this@UniListFragment)
            var isLoading = false
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                        if (currentPage < 3) {
                            isLoading = true
                            currentPage++
                            viewModel.fetchUniversityData(currentPage) { success ->
                                isLoading = !success
                            }
                            Log.e("Tag", "$currentPage")
                        }

                    }
                }
            })
        }

        binding.floatingActionButton.setOnClickListener {
            val cityAdapter = binding.recyclerViewCities.adapter as? CityAdapter
            cityAdapter?.collapseAllLists()
        }

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.cities.observe(viewLifecycleOwner, { cities ->

                val cityAdapter = binding.recyclerViewCities.adapter as? CityAdapter
                cityAdapter?.updateCities(cities)
                binding.recyclerViewCities.layoutManager?.onRestoreInstanceState(recyclerState)

        })

        viewModel.error.observe(viewLifecycleOwner, { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    override fun favClicked(university: University, binding: ItemUniversityBinding) {
        val isFav = !university.isFav

        university.isFav = isFav

        if (isFav) {
            viewModel.insert(university)
            Toast.makeText(
                requireContext(),
                "${university.name} favorilere eklendi",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.delete(university.name)
            Toast.makeText(
                requireContext(),
                "${university.name} favorilerden çıkarıldı",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.imageViewFavStar.setImageResource(if (isFav) R.drawable.enabled_fav_star else R.drawable.star)
    }

    override fun websiteClick(
        university: University,
        holder: UniversityAdapter.UniversityViewHolder
    ) {
        holder.itemView.findNavController()
            .navigate(UniListFragmentDirections.actionUniListFragmentToWebViewFragment(university))
    }


    override fun dialPhone(university: University) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${university.phone}"))
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        recyclerState = binding.recyclerViewCities.layoutManager?.onSaveInstanceState()
        outState.putParcelable("recycler_state", recyclerState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            recyclerState = it.getParcelable("recycler_state")
        }
    }
}