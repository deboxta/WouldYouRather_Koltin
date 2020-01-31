package ca.csf.mobile2.tp2.question

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.provider.Contacts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ca.csf.mobile2.tp2.R
import ca.csf.mobile2.tp2.databinding.ActivityAskQuestionBinding
import ca.csf.mobile2.tp2.question.viewModels.AskQuestionActivityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_ask_question.*
import okhttp3.ResponseBody
import org.androidannotations.annotations.*
import java.util.*

@SuppressLint("Registered") //Reason : Generated by Android Annotations
@DataBound
@OptionsMenu(R.menu.activity_ask_question)
@EActivity(R.layout.activity_ask_question)
class AskQuestionActivity : AppCompatActivity() {

    @BindingObject
    protected lateinit var binding: ActivityAskQuestionBinding

    @InstanceState
    protected lateinit var viewModel: AskQuestionActivityViewModel
    @InstanceState
    protected lateinit var questionData: QuestionData

    @ViewById(R.id.toolbar)
    protected lateinit var toolbar: Toolbar

    @Bean
    protected lateinit var questionService: QuestionService

    @AfterViews
    protected fun onCreate() {
        initView()
        if (!this::viewModel.isInitialized) {
            questionData = QuestionData()
            viewModel =
                AskQuestionActivityViewModel(
                    questionData
                )
            questionService.findRandomQuestion(
                this::onSuccess,
                this::onServerError,
                this::onConnectivityError
            )
            viewModel.isLoading = true
        }
        binding.viewModel = viewModel
    }

    @Click(R.id.choice1Button)
    protected fun onClickResponseButtonChoose1() {
        questionService.choose1(
            questionData,
            this::onSuccess,
            this::onServerError,
            this::onConnectivityError
        )
        viewModel.isLoading = true
    }

    @Click(R.id.choice2Button)
    protected fun onClickResponseButtonChoose2() {
        questionService.choose2(
            questionData,
            this::onSuccess,
            this::onServerError,
            this::onConnectivityError
        )
        viewModel.isLoading = true
    }

    @Click(R.id.choice1ResultBackground, R.id.choice2ResultBackground)
    protected fun onClickResultButton() {
        questionService.findRandomQuestion(
            this::onSuccess,
            this::onServerError,
            this::onConnectivityError
        )
        viewModel.isLoading = true
    }

    @Click(R.id.createButton)
    protected fun onClickCreateButton() {
        startActivityForResult(
            Intent(this, CreateQuestionActivity_::class.java),
            CREATE_QUESTION_REQUEST_CODE
        )
    }

    @Click(R.id.retryButton)
    protected fun onClickRetryButton() {
        questionService.findRandomQuestion(
            this::onSuccess,
            this::onServerError,
            this::onConnectivityError
        )
        viewModel.isLoading = true
    }

    @OptionsItem(R.id.flagButton)
    protected fun flag() {
        questionService.flagQuestion(
            questionData,
            this::onSuccess,
            this::onServerError,
            this::onConnectivityError
        )
    }

    private fun initView() {
        setSupportActionBar(toolbar)
    }

    private fun onSuccess(question: QuestionData) {
        questionData = question
        viewModel.isLoading = false
        viewModel.questionData = question
        if (!viewModel.isAskingQuestion) {
            viewModel.isAskingQuestion = true
        } else {
            viewModel.isQuestionAnswered = true
        }
    }

    private fun onSuccess(response: ResponseBody) {
        viewModel.isLoading = true
        Snackbar.make(rootView, R.string.text_reported, Snackbar.LENGTH_LONG).show()
        questionService.findRandomQuestion(
            this::onSuccess,
            this::onServerError,
            this::onConnectivityError
        )
    }

    private fun onServerError() {
        viewModel.isLoading = false
        viewModel.isErrorDetected = true
    }

    private fun onConnectivityError() {
        viewModel.isLoading = false
        viewModel.isErrorDetected = true
        viewModel.isConnectivityErrorDetected = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            viewModel.isAskingQuestion = false
            val id: UUID = data!!.getSerializableExtra(EXTRA_NAME) as UUID
            questionService.findQuestionById(
                id,
                this::onSuccess,
                this::onServerError,
                this::onConnectivityError
            )
        }
    }
}

private const val CREATE_QUESTION_REQUEST_CODE = 1
private const val EXTRA_NAME = "QUESTION"
