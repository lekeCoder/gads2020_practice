package com.gads2020.practice.models

data class Learner(
    val hours: Int,
    var score: Int = 0,
    val name: String,
    val country: String,
    val badgeUrl: String
)