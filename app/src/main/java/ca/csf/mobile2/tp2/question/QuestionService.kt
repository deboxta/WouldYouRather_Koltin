package ca.csf.mobile2.tp2.question

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.ResponseBody
import org.androidannotations.annotations.Background
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.UiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.IOException
import java.util.*


@EBean(scope = EBean.Scope.Singleton)
class QuestionService {

    private val service: Service

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .build()

        service = retrofit.create()
    }

    @Background
    fun findRandomQuestion(
        onSuccess: (QuestionData) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ) {
        service.findRandomQuestion().execute(
            onSuccess,
            onServerError,
            onConnectivityError
        )
    }

    @Background
    fun createQuestion(
        question: QuestionData,
        onSuccess: (QuestionData) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ) {
        service.createQuestion(question).execute(
            onSuccess,
            onServerError,
            onConnectivityError
        )
    }

    @Background
    fun flagQuestion(
        question: QuestionData,
        onSuccess: (ResponseBody) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ) {
        if (question.id != null) {
            service.flagQuestion(question.id!!).execute(
                onSuccess,
                onServerError,
                onConnectivityError
            )
        }
    }

    @Background
    fun findQuestionById(
        id: UUID,
        onSuccess: (QuestionData) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ) {
        service.findQuestionById(id).execute(
            onSuccess,
            onServerError,
            onConnectivityError
        )
    }

    @Background
    fun choose1(
        question: QuestionData,
        onSuccess: (QuestionData) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ) {
        if (question.id != null) {
            service.choose1(question.id!!).execute(
                onSuccess,
                onServerError,
                onConnectivityError
            )
        }
    }

    @Background
    fun choose2(
        question: QuestionData,
        onSuccess: (QuestionData) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ) {
        if (question.id != null) {
            service.choose2(question.id!!).execute(
                onSuccess,
                onServerError,
                onConnectivityError
            )
        }
    }

    @UiThread
    protected fun doInUIThread(callback: () -> Unit) {
        callback()
    }

    private fun <T> Call<T>.execute(
        onSuccess: (T) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ) {
        try {
            val response = this.execute()
            if (response.isSuccessful) {
                val result = response.body()!!
                doInUIThread { onSuccess(result) }
            } else {
                doInUIThread { onServerError() }
            }
        } catch (e: IOException) {
            doInUIThread { onConnectivityError() }
        }
    }

    private interface Service {
        @GET("/api/v1/question/random")
        fun findRandomQuestion(): Call<QuestionData>

        @POST("/api/v1/question")
        fun createQuestion(@Body question: QuestionData): Call<QuestionData>

        @POST("/api/v1/question/{id}/flag")
        fun flagQuestion(@Path("id") id: UUID): Call<ResponseBody>

        @GET("/api/v1/question/{id}")
        fun findQuestionById(@Path("id") id: UUID): Call<QuestionData>

        @POST("/api/v1/question/{id}/choose1")
        fun choose1(@Path("id") id: UUID): Call<QuestionData>

        @POST("/api/v1/question/{id}/choose2")
        fun choose2(@Path("id") id: UUID): Call<QuestionData>
    }
}

private const val URL = "http://10.200.82.153:8080"