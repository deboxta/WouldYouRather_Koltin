package ca.csf.mobile2.tp2.question

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.androidannotations.annotations.Background
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.UiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.io.IOException


@EBean(scope = EBean.Scope.Singleton)
class QuestionService {

    private val service : Service

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .build()

        service = retrofit.create()
    }

    @Background
    fun findRandomQuestion(
        onSuccess : (QuestionData) -> Unit,
        onServerError : () -> Unit,
        onConnectivityError : () -> Unit
    ){
        service.findRandomQuestion().execute(
            onSuccess,
            onServerError,
            onConnectivityError
        )
    }

    @UiThread
    protected fun doInUIThread(callback : () -> Unit){
        callback()
    }

    private fun <T> Call<T>.execute(
        onSuccess: (T) -> Unit,
        onServerError: () -> Unit,
        onConnectivityError: () -> Unit
    ){
        try {
            val response = this.execute()
            if (response.isSuccessful){
                val result = response.body()!!
                doInUIThread { onSuccess(result) }
            } else {
                doInUIThread { onServerError() }
            }
        } catch (e : IOException){
            doInUIThread { onConnectivityError() }
        }
    }

    private interface Service {
        @GET("/api/v1/question/random")
        fun findRandomQuestion() : Call<QuestionData>
    }
}

private const val URL = "http://10.200.82.153:8080"