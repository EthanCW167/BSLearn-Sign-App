package com.example.composettest.Domain.use_case

import com.example.composettest.Domain.model.signData
import com.example.composettest.Domain.repository.LessonRepository

class GetSignDataByIdUseCase(
    private val repository: LessonRepository
) {

    suspend operator fun invoke(signId: Int): signData? {
        return repository.getSignDataBySignId(signId)
    }
}