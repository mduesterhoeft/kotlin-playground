package com.example.kotlinplayground

import java.math.BigDecimal
import java.util.*

data class Order(val id: Long, val productLineItems: List<ProductLineItem>) {

    fun getGrandTotal() = productLineItems.map {it.getLineItemPrice().amount }.reduce {a, b -> a.plus(b)}
}

data class ProductLineItem(val quantity: Int, val product: Product) {

    fun getLineItemPrice() = Price(product.price.amount.multiply(BigDecimal.valueOf(quantity.toLong())), product.price.currency)
}

data class Product(val id: Long, val name: String, val price: Price)

data class Price(val amount: BigDecimal, val currency: Currency)
