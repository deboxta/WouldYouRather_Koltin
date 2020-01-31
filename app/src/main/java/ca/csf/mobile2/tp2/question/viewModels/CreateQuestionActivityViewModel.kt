package ca.csf.mobile2.tp2.question.viewModels

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import ca.csf.mobile2.tp2.question.QuestionData
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.Transient

@Parcel(Parcel.Serialization.BEAN)
class CreateQuestionActivityViewModel @ParcelConstructor constructor(
    val questionData: QuestionData
) : BaseObservable() {

    @get:Transient
    var questionText: String?
        get() = questionData.text
        set(value) {
            questionData.text = value
        }

    @get:Transient
    var choice1Text: String?
        get() = questionData.choice1
        set(value) {
            questionData.choice1 = value
        }

    @get:Transient
    var choice2Text: String?
        get() = questionData.choice2
        set(value) {
            questionData.choice2 = value
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