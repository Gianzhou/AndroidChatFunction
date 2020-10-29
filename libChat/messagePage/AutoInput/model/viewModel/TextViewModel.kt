package com.somoplay.wefungame.libChat.messagePage.AutoInput.model.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.TextModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.repository.TextRepository

class TextViewModel: AndroidViewModel {

    private var mTextRepository: TextRepository

    constructor(application: Application): super(application){
        mTextRepository = TextRepository(application)
    }

    fun getSameCategoryTextItems(id:Long): LiveData<List<TextModel>> {
        return mTextRepository.getSameCategoryTextItem(id)

    }
}