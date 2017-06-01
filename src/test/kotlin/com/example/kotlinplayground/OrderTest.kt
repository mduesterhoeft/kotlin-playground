package com.example.kotlinplayground

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test
import java.math.BigDecimal
import java.util.*


class OrderTest {

    @Test
    fun `should compute grand total`() {
        val grandTotal = Order(
                id = 1,
                productLineItems = listOf(
                        ProductLineItem(2, Product(1, "shirt", Price(BigDecimal.TEN, Currency.getInstance("EUR")))),
                        ProductLineItem(1, Product(2, "jeans", Price(BigDecimal.ONE, Currency.getInstance("EUR"))))
                )
        ).getGrandTotal()

        then(grandTotal).isEqualTo(BigDecimal.valueOf(21))
    }
}