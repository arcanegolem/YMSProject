package arcanegolem.yms.core.ui.components.time_picker

data class PickedTime(
  val hour : Int,
  val minute : Int
) {
  fun formatToTimeString() : String {
    var formattedHour = ""
    repeat(2 - hour.toString().length) { formattedHour += "0" }
    formattedHour += hour.toString()

    var formattedMinute = ""
    repeat(2 - minute.toString().length) { formattedMinute += "0" }
    formattedMinute += minute.toString()

    return "$formattedHour:$formattedMinute"
  }
}
