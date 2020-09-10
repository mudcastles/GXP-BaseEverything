package com.example.gxp_baseeverything.model

import androidx.databinding.Bindable

data class MultiModel(
    var first: First,
    var string: String?
)

data class First(
    var second: Second
)

data class Second(
    var third: Third
)

data class Third(
    var fourth: Fourth
)

data class Fourth(
    var string: String?
)
