package com.example.newcekirge.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "uni_table")
data class University(
    @PrimaryKey(autoGenerate = true) var uniId: Int,
    val adress: String,
    val email: String,
    val fax: String,
    val name: String,
    val phone: String,
    val rector: String,
    val website: String,
    var isFav :Boolean=false
): Parcelable