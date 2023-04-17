package com.example.composettest.Domain.util

sealed class QuestionOrder(val orderType: OrderType) {

    class orderNum(orderType: OrderType): QuestionOrder(orderType)
}
