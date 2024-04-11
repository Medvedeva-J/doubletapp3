import android.database.Observable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
class Habit (
    var title: String,
    val description: String,
    val priority: Int,
    val type: String,
    val repeat: Int,
    val days: Int,
    val edit_date: Date,
    var position: Int? = null) : Parcelable {
}