package com.example.composettest.Domain.util

sealed class LessonOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): LessonOrder(orderType)
    class LessonNum(orderType: OrderType): LessonOrder(orderType)
}
