package com.example.kotlinplayground

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


//@RestController
@RequestMapping("orders")
class OrderController(val orderRepository: OrderRepository) {

    @GetMapping
    fun getOrders(): Flux<Order> {
        return orderRepository.findAll()
    }

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: Long): Mono<Order> {
        return orderRepository.findById(orderId)
    }
}