package ca.csf.mobile2.tp2.question

import org.parceler.Parcel
import java.util.*

@Parcel(Parcel.Serialization.BEAN)
data class QuestionData(
    val choice1 : String = "",
    val choice2 : String = "",
    val id : UUID = UUID.randomUUID(),
    val nbChoice1 : Int = 0,
    val nbChoice2 : Int = 0,
    val text : String = ""
)

