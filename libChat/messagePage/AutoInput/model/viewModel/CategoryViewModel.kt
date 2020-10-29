package com.somoplay.wefungame.libChat.messagePage.AutoInput.model.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.CategoryModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.TextModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.repository.CategoryRepository
import java.util.*

class CategoryViewModel: AndroidViewModel{
    private var mCategoryRepository: CategoryRepository

    constructor(application: Application): super(application) {
        mCategoryRepository = CategoryRepository(application)
    }

    fun getAllCategories(): LiveData<List<CategoryModel>>{
        return mCategoryRepository.getAllCategories()
    }
}