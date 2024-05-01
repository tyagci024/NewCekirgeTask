package com.example.newcekirge.service

import com.example.newcekirge.Utilies.Constants
import com.example.newcekirge.model.UniversityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UniversityService {

    @GET(Constants.END_POINT_UNI)
    suspend fun getUniversities(@Path("page") page: String): UniversityResponse
}

