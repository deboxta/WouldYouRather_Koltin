package ca.csf.mobile2.tp2.question

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import java.util.*

@Parcel(Parcel.Serialization.BEAN)
data class QuestionData @ParcelConstructor constructor(
    var choice1 : String?,
    var choice2 : String?,
    val id : UUID?,
    val nbChoice1 : Int?,
    val nbChoice2 : Int?,
    var text : String?
){
    private val changeListeners: MutableList<() -> Unit> = mutableListOf()
    private val hasListeners get() = changeListeners.size > 0

    constructor(): this(null,null,null,null,null,null)

    fun addChangeListener(listener: () -> Unit) {
        changeListeners.add(listener)
    }

    fun removeChangeListener(listener: () -> Unit) {
        changeListeners.remove(listener)
    }

    private fun notifyChanged() {
        if (hasListeners) {
            for (listener in changeListeners) listener()
        }
    }
}