package com.somoplay.wefungame.libChat.messagePage.AutoInput.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.CategoryModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.dao.CategoryDao
import com.somoplay.wefungame.main.WeFunDatabase
import java.util.*

class CategoryRepository {
    private var db: WeFunDatabase
    private var mCategoryDao:  CategoryDao

    constructor(context: Context){
        db = WeFunDatabase.invoke(context)
        mCategoryDao = db.CategoryDao()
    }

    fun selectCategory(name : String): CategoryModel{
        return mCategoryDao.selectCategory(name)
    }
    fun getAllCategories(): LiveData<List<CategoryModel>>{
        return mCategoryDao.loadAll()
    }

    fun insertCategory(newName: String, oldName: String): Boolean{
        var newModel = selectCategory(newName)
        var oldModel : CategoryModel = selectCategory(oldName)
        if(newModel.isEmpty()){
            oldModel.setName(newName)
            mCategoryDao.insertCategory(oldModel)
            return true
        }
        return false

    }
    fun updateCategory(newName: String, oldName: String): Boolean{
        var newModel : CategoryModel = selectCategory(newName)
        var oldModel : CategoryModel = selectCategory(oldName)
        if(newModel.isEmpty()){
            oldModel.setName(newName)
            mCategoryDao.updateCategory(oldModel)
            return true
        }
        return false
    }

    fun deleteCategory(name: String){
        var model: CategoryModel = selectCategory(name)
        mCategoryDao.deleteCategory(model)

    }
}