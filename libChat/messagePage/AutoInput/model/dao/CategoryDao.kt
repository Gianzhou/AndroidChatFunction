package com.somoplay.wefungame.libChat.messagePage.AutoInput.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.CategoryModel
@Dao
interface CategoryDao {

    @Query("SELECT * FROM gm_message_autoinput_category ORDER BY id")
    fun loadAll(): LiveData<List<CategoryModel>>

    @Query("SELECT * FROM gm_message_autoinput_category ORDER BY id")
    fun loadAllN(): List<CategoryModel>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCategory(categoryModel: CategoryModel)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCategoriesList(categoryList: List<CategoryModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCategory(categoryModel: CategoryModel)

    @Delete
    fun deleteCategory(categoryModel: CategoryModel)

    @Query("SELECT * FROM gm_message_autoinput_category WHERE categoryName = :name")
    fun selectCategory(name: String): CategoryModel

    @Query("SELECT * FROM gm_message_autoinput_category WHERE categoryName = :name")
    fun selectLiveDataCategory(name : String): LiveData<CategoryModel>
}