package com.keyword.keyword_miner.domain.usecase

import android.util.Log
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.domain.repository.RoomRepository
import javax.inject.Inject

class GetSavedKeywordUsecase @Inject constructor(
    private var roomRepository: RoomRepository
) {
    operator suspend fun invoke() : List<KeywordSaveModel>{
        Log.d("Storage", " Usecase invoke: ${roomRepository.getData()}")
        return roomRepository.getData()

    }
}