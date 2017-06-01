package com.example.kotlinplayground

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.net.URI

@Component
class OrderHandler(val orderRepository: OrderRepository) {

    fun orders() = HandlerFunction { ServerResponse.ok().body(orderRepository.findAll())}

    fun order() = HandlerFunction { request ->
        orderRepository.findById(request.pathVariable("orderId").toLong())
                .flatMap { ServerResponse.ok().body(Mono.just(it)) }
                .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun addOrder() = HandlerFunction { request ->
        request.bodyToMono(OrderResource::class.java).flatMap {
            val savedOrder = orderRepository.add(it)
            ServerResponse.created(URI.create(savedOrder.id.toString())).build()
        }
    }
}

data class OrderResource @JsonCreator constructor (val lineItems: List<LineItemResource>)
data class LineItemResource @JsonCreator constructor (val product: Long, val quantity: Int = 1)
