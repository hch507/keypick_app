package com.keyword.keyword_miner.data.repositoryImpl

import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.data.local.Room.RoomhelperHilt
import com.keyword.keyword_miner.domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private var roomhelper: RoomhelperHilt
) : RoomRepository {
    override suspend fun getData() : List<KeywordSaveModel> {
       return  roomhelper.roomDao().getAll()
    }

    override suspend fun InsertData(saveData: KeywordSaveModel) {
        roomhelper.roomDao().insert(saveData)
    }


    override suspend fun DeleteData() {
        TODO("Not yet implemented")
    }

}