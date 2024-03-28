import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Habit (
    var title: String, val description: String,
    val priority: Int, val type: String,
    val repeat: Int, val days: Int,
    var position: Int? = null) : Parcelable {
}