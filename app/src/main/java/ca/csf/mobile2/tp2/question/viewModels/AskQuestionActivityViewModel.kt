package ca.csf.mobile2.tp2.question.viewModels

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import ca.csf.mobile2.tp2.question.QuestionData
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.Transient

@Parcel(Parcel.Serialization.BEAN)
class AskQuestionActivityViewModel @ParcelConstructor constructor(
    questionData: QuestionData
) : BaseObservable() {

    var questionData = questionData
        set(value) {
            field.removeChangeListener(this::notifyChange)
            field = value
            field.addChangeListener(this::notifyChange)
            notifyChange()
        }

    @get:Transient
    val titleText: String?
        get() = questionData.text

    @get:Transient
    val choice1Text: String?
        get() = questionData.choice1

    @get:Transient
    val choice2Text: String?
        get() = questionData.choice2

    @get:Transient
    val choice1Statistic: String?
        get() =
            if (questionData.nbChoice1 != 0 || questionData.nbChoice2 != 0) {
                (questionData.nbChoice1 * PERCENT / (questionData.nbChoice1 + questionData.nbChoice2)).toString() + PERCENT_SYMBOL
            } else {
                null
            }

    @get:Transient
    val choice2Statistic: String?
        get() =
            if (questionData.nbChoice1 != 0 || questionData.nbChoice2 != 0) {
                (questionData.nbChoice2 * PERCENT / (questionData.nbChoice1 + questionData.nbChoice2)).toString() + PERCENT_SYMBOL
            } else {
                null
            }

    var isAskingQuestion: Boolean = false
        set(value) {
            if (value) {
                isErrorDetected = false
                isQuestionAnswered = false
            }
            field = value

            notifyChange()
        }

    var isQuestionAnswered: Boolean = false
        set(value) {
            if (value) {
                isAskingQuestion = false
                isErrorDetected = false
            }
            field = value
            notifyChange()
        }

    var isErrorDetected: Boolean = false
        set(value) {
            if (value) {
                isAskingQuestion = false
                isQuestionAnswered = false
            }
            field = value
            notifyChange()
        }

    var isLoading: Boolean = false

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)

        questionData.addChangeListener(this::notifyChange)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)

        questionData.removeChangeListener(this::notifyChange)
    }
}

private const val PERCENT = 100
private const val PERCENT_SYMBOL = "%"