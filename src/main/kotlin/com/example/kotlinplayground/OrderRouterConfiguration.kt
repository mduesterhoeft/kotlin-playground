package com.example.kotlinplayground

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.RouterFunctions.route

@Configuration
class OrderRouterConfiguration {

    @Bean
    fun orderRouter(orderHandler: OrderHandler) =
        nest(path("/orders"),
            route(POST("/"), orderHandler.addOrder())
            .andRoute(GET("/"), orderHandler.orders())
            .andRoute(GET("/{orderId}"), orderHandler.order())
        )
}
