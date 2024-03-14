import android.graphics.Color
import kotlinx.serialization.Serializable

class Habit (
    var title: String, val description: String,
    val priority: Int, val type: String,
    val repeat: Int, val days: Int,
    var position: Int? = null,
    val color: Color? =  null) : java.io.Serializable {
}