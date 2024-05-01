package com.example.newcekirge.repository

import androidx.lifecycle.LiveData
import com.example.newcekirge.model.University
import com.example.newcekirge.model.UniversityResponse
import com.example.newcekirge.room.UniDao
import com.example.newcekirge.room.UniDatabase
import com.example.newcekirge.service.UniversityService
import javax.inject.Inject

class Repository @Inject constructor(
    private val uniDao: UniDao,
    private val universityService: UniversityService

) {

    suspend fun insert(university: University) {
        uniDao.insertUni(university)
    }

    suspend fun delete(name: String) {
        uniDao.deleteUniByName(name)
    }

    suspend fun isUniInDatabase(name: String): Int {
        return uniDao.isUniNameExist(name)
    }

    fun getAllFav(): LiveData<List<University>> {
        return uniDao.getAllUnis()
    }

    suspend fun fetchUniversityData(page: Int) = universityService.getUniversities(page.toString())
}
