package com.example.imagesearch.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.imagesearch.R
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.example.imagesearch.presentation.ListItem
class LikeRepository(context: Context) {

    private val gson =
        GsonBuilder().registerTypeAdapter(ListItem::class.java, CustomDeserializer()).create()
    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_key),
        Context.MODE_PRIVATE
    )

    private val _likedList = loadLikedList()
    val likedList get() = _likedList.toList()

    fun addLike(item: ListItem) {
        if (!_likedList.contains(item)) {
            _likedList.add(0, item)
            saveLikedList()
        }
    }

    fun removeLike(item: ListItem) {
        _likedList.remove(item)
        saveLikedList()
    }

    private fun loadLikedList(): MutableList<ListItem> {
        val jsonString = sharedPreferences.getString("likedItem", null) ?: return mutableListOf()
        Log.d("라이크1","$jsonString")
        val listType = object : TypeToken<MutableList<ListItem>>() {}.type

        return try {
            val list : MutableList<ListItem> = gson.fromJson(jsonString, listType)
            Log.d("로드 성공","$list")
            list
        } catch (e: Exception) {
            Log.d("로드 실패","실패")
            mutableListOf()
        }
    }

    private fun saveLikedList() {
        val jsonString = gson.toJson(_likedList)
        sharedPreferences.edit().putString("likedItem", jsonString).apply()
    }
}