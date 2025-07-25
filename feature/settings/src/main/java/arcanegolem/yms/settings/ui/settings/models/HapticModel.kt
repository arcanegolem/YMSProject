package arcanegolem.yms.settings.ui.settings.models

import androidx.annotation.StringRes

internal data class HapticModel(
  val pattern : LongArray,
  @param:StringRes val nameResId : Int
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    
    other as HapticModel
    
    if (nameResId != other.nameResId) return false
    if (!pattern.contentEquals(other.pattern)) return false
    
    return true
  }
  
  override fun hashCode(): Int {
    var result = nameResId
    result = 31 * result + pattern.contentHashCode()
    return result
  }
}