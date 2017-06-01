package com.example.kotlinplayground

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner::class)
@AutoConfigureWebTestClient
@AutoConfigureWebClient
@AutoConfigureWebFlux
class OrderRestIntegrationTest {

    @LocalServerPort
    var port: Int? = null

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var webClient: WebClient

    @Test
    fun `should get order`() {
        webTestClient.get().uri("http://localhost:$port/orders/1").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("id").isEqualTo(1)
                .consumeWith {
                    print(it)
                }
    }

    @Test
    fun `should add order`() {
        val products = listOf(Pair(1, 2), Pair(2, 3))
        webClient.post()
                .uri("http://localhost:$port/orders")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .syncBody(
                        """
                        {
                            "lineItems": [
                                {"product": ${products[0].first}, "quantity":${products[0].second}},
                                {"product": ${products[1].first}}
                            ]
                        }
                        """
                ).exchange();
    }
}

