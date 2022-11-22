package com.example.sample.infra

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.CompiledField
import com.apollographql.apollo3.api.Executable
import com.apollographql.apollo3.cache.normalized.api.*
import com.apollographql.apollo3.cache.normalized.logCacheMisses
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import com.example.sample.BuildConfig.GITHUB_PERSONAL_ACCESS_TOKEN
import okhttp3.OkHttpClient

object ApolloClientFactory {
    private const val SERVER_URL = "https://api.github.com/graphql"
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val HEADER_AUTHORIZATION_BEARER = "Bearer"

    fun create(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder()
            .authenticator { _, response ->
                response.request.newBuilder().addHeader(HEADER_AUTHORIZATION, "$HEADER_AUTHORIZATION_BEARER $GITHUB_PERSONAL_ACCESS_TOKEN").build()
            }.build()

        return ApolloClient.Builder()
            .serverUrl(SERVER_URL)
            .okHttpClient(okHttpClient)
            .logCacheMisses()
            .normalizedCache(
                normalizedCacheFactory = MemoryCacheFactory(),
                cacheKeyGenerator = object : CacheKeyGenerator {
                    override fun cacheKeyForObject(
                        obj: Map<String, Any?>,
                        context: CacheKeyGeneratorContext
                    ): CacheKey? {
                        return obj["id"]?.toString()?.let { CacheKey(it) }
                            ?: TypePolicyCacheKeyGenerator.cacheKeyForObject(obj, context)
                    }
                },
                cacheResolver = object : CacheResolver {
                    override fun resolveField(
                        field: CompiledField,
                        variables: Executable.Variables,
                        parent: Map<String, Any?>,
                        parentId: String
                    ): Any? {
                        return field.resolveArgument("id", variables)?.toString()
                            ?.let { CacheKey(it) }
                            ?: FieldPolicyCacheResolver.resolveField(field, variables, parent, parentId)
                    }
                }
            )
            .build()
    }
}