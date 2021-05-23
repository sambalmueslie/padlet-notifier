package de.sambalmueslie.padlet.client.wishes

import de.sambalmueslie.padlet.config.PadletConfiguration
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.netty.handler.codec.http.HttpHeaderNames
import javax.inject.Singleton

@Singleton
class WishesClient(
    @param:Client(PadletConfiguration.PADLET_API_URL) private val httpClient: RxHttpClient,
    configuration: PadletConfiguration
) {

    fun fetchWishes(wallId: Long, next: String = ""): WishesPageResult? {
        val uri = UriBuilder.of(PadletConfiguration.PADLET_API_URL)
            .path("wishes")
            .queryParam("wall_id", wallId)
        if (next.isNotBlank()) {
            uri.queryParam("page_start", next)
        }
        val response = httpClient.exchange(GET<Any>(uri.build()), Argument.of(WishesPage::class.java))
            .retry(3)
            .blockingFirst()
        val etag = response.header(HttpHeaderNames.ETAG) ?: ""
        val page = response.body() ?: return null
        return WishesPageResult(page, etag)

    }

}
