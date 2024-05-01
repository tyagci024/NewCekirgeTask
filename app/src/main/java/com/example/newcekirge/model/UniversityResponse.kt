package com.example.newcekirge.model

data class UniversityResponse(
    val currentPage: Int,
    val `data`: List<City>,
    val itemPerPage: Int,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int
)