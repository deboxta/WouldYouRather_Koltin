package ca.csf.mobile2.tp2.question.viewModels

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import ca.csf.mobile2.tp2.question.QuestionData
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.Transient

@Parcel(Parcel.Serialization.BEAN)
class AskQuestionActivityViewModel @ParcelConstructor constructor(
    val questionData: QuestionData
) : BaseObservable() {

    @get:Transient
    val choice1Text : String?
        get() = questionData.choice1

    @get:Transient
    val choice2Text : String?
        get() = questionData.choice2

    var isAskingQuestion : Boolean = false
        set(value) {
            if (value){
                isErrorDetected = false
                isQuestionAnswered = false
            }
            field = value
        }

    var isQuestionAnswered : Boolean = false
        set(value) {
            if (value){
                isAskingQuestion = false
                isErrorDetected = false
            }
            field = value
        }

    var isErrorDetected : Boolean = false
        set(value) {
            if (value){
                isAskingQuestion = false
                isQuestionAnswered = false
            }
            field = value
        }

    var isLoading : Boolean = false

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)

        questionData.addChangeListener(this::notifyChange)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)

        questionData.removeChangeListener(this::notifyChange)
    }
}