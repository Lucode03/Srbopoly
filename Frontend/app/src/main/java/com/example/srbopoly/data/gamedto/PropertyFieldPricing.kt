package com.example.srbopoly.data.gamedto

import com.example.srbopoly.data.fields.PropertyField

val PropertyField.housePrice: Int get() = Price / 2
val PropertyField.mortgageValue: Int get() = Price / 2
val PropertyField.unmortgageCost: Int get() = kotlin.math.ceil(mortgageValue * 1.1).toInt()