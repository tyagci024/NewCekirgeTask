package com.example.newcekirge.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newcekirge.model.University
@Dao
interface UniDao {

    @Insert
    suspend fun insertUni(university: University)

    @Query("SELECT * FROM uni_table")
    fun getAllUnis(): LiveData<List<University>>

    @Query("DELETE FROM uni_table WHERE name = :name")
    suspend fun deleteUniByName(name: String)

    @Query("DELETE FROM uni_table")
    suspend fun deleteAllUni()

    @Query("SELECT COUNT(*) FROM uni_table WHERE name = :name")
    suspend fun isUniNameExist(name: String): Int

    @Query("SELECT EXISTS(SELECT 1 FROM uni_table WHERE name = :name LIMIT 1)")
    fun isUniInDatabase(name: String): LiveData<Boolean>
}