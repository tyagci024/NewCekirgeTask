package com.example.newcekirge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import com.example.newcekirge.model.City
import com.example.newcekirge.model.University
import com.example.newcekirge.repository.Repository
import com.example.newcekirge.service.UniversityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniListViewModel @Inject constructor(application: Application, private val repository: Repository) : AndroidViewModel(application) {
    val error: MutableLiveData<String> = MutableLiveData()
    val cities = MutableLiveData<List<City>>()
    var universityFavList: LiveData<List<University>> = repository.getAllFav()

    init{
        fetchUniversityData(1)
    }

    fun fetchUniversityData(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.fetchUniversityData(page)
                val citiesList = response.data
                val currentCities = cities.value //diğer pageler için istek atılma durumunda işe yarıyor
                    ?: emptyList()

                for (city in citiesList) {
                    for (university in city.universities) {
                        val isUniInDatabase = repository.isUniInDatabase(university.name)
                        if (isUniInDatabase > 0) {
                            university.isFav = true
                        }
                        Log.d("Tag", "${university.isFav}")
                    }
                }

                val updatedCitiesList = currentCities.toMutableList()
                updatedCitiesList.addAll(citiesList)

                cities.value = updatedCitiesList

            } catch (e: Exception) {
                error.value = "Veriler yüklenirken bir hata oluştu: ${e.message}"
            }
        }
    }


    fun insert(university: University) {
        viewModelScope.launch {
            repository.insert(university)
        }
    }

    fun delete(name: String) {
        viewModelScope.launch {
            repository.delete(name)
        }
    }
}

