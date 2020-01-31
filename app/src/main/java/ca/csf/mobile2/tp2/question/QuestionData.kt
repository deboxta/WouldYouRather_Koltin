package ca.csf.mobile2.tp2.question

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import java.util.*

@Parcel(Parcel.Serialization.BEAN)
class QuestionData @ParcelConstructor constructor(
    id: UUID?,
    text: String,
    choice1: String,
    choice2: String,
    nbChoice1: Int,
    nbChoice2: Int
) {
    var id = id
        set(value) {
            field = value
            notifyChanged()
        }

    var text = text
        set(value) {
            field = value
            notifyChanged()
        }

    var choice1 = choice1
        set(value) {
            field = value
            notifyChanged()
        }

    var choice2 = choice2
        set(value) {
            field = value
            notifyChanged()
        }

    var nbChoice1 = nbChoice1
        set(value) {
            field = value
            notifyChanged()
        }

    var nbChoice2 = nbChoice2
        set(value) {
            field = value
            notifyChanged()
        }

    private val changeListeners: MutableList<() -> Unit> = mutableListOf()
    private val hasListeners get() = changeListeners.size > 0

    constructor() : this(null, "", "", "", 0, 0)

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