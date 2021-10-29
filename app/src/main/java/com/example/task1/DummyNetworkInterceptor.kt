package com.example.task1

import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType

private val DUMMY_RESULTS = listOf(
    "Hello, coroutines!",
    "Hello, BCA",
    "Hallo, Android"
)


class DummyNetworkInterceptor : Interceptor {
    private var lastResult: String = ""
    val gson = Gson()

    private var attempts = 0

    private fun wantRandomError() = attempts++ % 5 == 0

    override fun intercept(chain: Interceptor.Chain): Response {
        pretendToBlockForNetworkRequest()
        return if (wantRandomError()) {
            makeErrorResult(chain.request())
        } else {
            makeOkResult(chain.request())
        }
    }

    private fun pretendToBlockForNetworkRequest() = Thread.sleep(500)

    private fun makeErrorResult(request: Request): Response {
        return Response.Builder()
            .code(500)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("Bad server day")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                gson.toJson(mapOf("cause" to "not sure"))))
            .build()
    }

    private fun makeOkResult(request: Request): Response {
        var nextResult = lastResult
        while (nextResult == lastResult) {
            nextResult = DUMMY_RESULTS.random()
        }
        lastResult = nextResult
        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                gson.toJson(nextResult)))
            .build()
    }
}