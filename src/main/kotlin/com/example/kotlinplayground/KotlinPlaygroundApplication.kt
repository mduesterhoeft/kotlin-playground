package com.example.kotlinplayground

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class KotlinPlaygroundApplication

    fun main(args: Array<String>) {
        SpringApplication.run(KotlinPlaygroundApplication::class.java, *args)
    }
