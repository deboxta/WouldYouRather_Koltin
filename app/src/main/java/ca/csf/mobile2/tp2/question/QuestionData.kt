package ca.csf.mobile2.tp2.question

import org.parceler.Parcel
import java.util.*

@Parcel(Parcel.Serialization.BEAN)
data class QuestionData(
    val choice1 : String,
    val choice2 : String,
    val id : UUID,
    val nbChoice1 : Int,
    val nbChoice2 : Int,
    val text : String
)