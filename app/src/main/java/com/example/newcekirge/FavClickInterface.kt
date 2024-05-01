package com.example.newcekirge

import com.example.newcekirge.adapter.UniversityAdapter
import com.example.newcekirge.databinding.ItemUniversityBinding
import com.example.newcekirge.model.University

interface FavClickInterface {
    fun favClicked(university: University,binding: ItemUniversityBinding)
    fun websiteClick(university: University,holder: UniversityAdapter.UniversityViewHolder)
    fun dialPhone(university: University)
}