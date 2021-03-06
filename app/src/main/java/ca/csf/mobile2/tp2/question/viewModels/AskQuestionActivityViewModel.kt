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
    val titleText: String
        get() = questionData.text

    @get:Transient
    val choice1Text: String
        get() = questionData.choice1

    @get:Transient
    val choice2Text: String
        get() = questionData.choice2

    @get:Transient
    val choice1Statistic: Int
        get() =
            if (questionData.nbChoice1 != 0 || questionData.nbChoice2 != 0) {
                (questionData.nbChoice1 * 100 / (questionData.nbChoice1 + questionData.nbChoice2))
            } else {
                questionData.nbChoice1
            }

    @get:Transient
    val choice2Statistic: Int
        get() =
            if (questionData.nbChoice1 != 0 || questionData.nbChoice2 != 0) {
                (questionData.nbChoice2 * 100 / (questionData.nbChoice1 + questionData.nbChoice2))
            } else {
                questionData.nbChoice2
            }

    var isAskingQuestion: Boolean = false
        set(value) {
            if (field != value) {
                isErrorDetected = false
                isQuestionAnswered = false
                isFlagging = false

                field = value

                notifyChange()
            }
        }

    var isQuestionAnswered: Boolean = false
        set(value) {
            if (field != value) {
                isAskingQuestion = false
                isErrorDetected = false
                isFlagging = false

                field = value

                notifyChange()
            }
        }

    var isErrorDetected: Boolean = false
        set(value) {
            if (field != value) {
                isAskingQuestion = false
                isQuestionAnswered = false
                isConnectivityErrorDetected = false
                isFlagging = false

                field = value

                notifyChange()
            }
        }

    var isConnectivityErrorDetected: Boolean = false
        set(value) {
            field = value
            notifyChange()
        }

    var isFlagging: Boolean = false
        set(value) {
            if (field != value) {
                isAskingQuestion = false
                isQuestionAnswered = false
                isErrorDetected = false

                field = value

                notifyChange()
            }
        }

    var isLoading: Boolean = false
        set(value) {
            field = value

            notifyChange()
        }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)

        questionData.addChangeListener(this::notifyChange)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)

        questionData.removeChangeListener(this::notifyChange)
    }
}