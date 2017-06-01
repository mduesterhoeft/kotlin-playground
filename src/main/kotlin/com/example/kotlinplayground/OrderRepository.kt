package com.example.kotlinplayground

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN
import java.util.*

@Repository
class OrderRepository {

    private val product1 =  Product(1, "shirt", Price(TEN, Currency.getInstance("EUR")))
    private val product2 =  Product(2, "jeans", Price(ONE, Currency.getInstance("EUR")))

    private val productMap = mapOf(Pair(product1.id, product1), Pair(product2.id, product2))

    val orders = mutableListOf(
            Order(
                    id = 1,
                    productLineItems = listOf(
                            ProductLineItem(2, product1),
                            ProductLineItem(1, product2)
                    )
            ),
            Order(
                    id = 2,
                    productLineItems = listOf(
                            ProductLineItem(2, product1),
                            ProductLineItem(1, product2)
                    )
            )
    )

    fun findAll(): Flux<Order> = Flux.fromIterable(orders)

    fun findById(id: Long): Mono<Order> = Mono.justOrEmpty(orders.filter { it.id == id }.firstOrNull())

    fun add(order: OrderResource): Order {
        val orderToSave = Order(
                id = (orders.map{ it.id }.max() ?: 0 + 1),
                productLineItems = order.lineItems.map { ProductLineItem(it.quantity, productMap[it.product]!!) }
        )
        orders.add(orderToSave)
        return orderToSave
    }
}

