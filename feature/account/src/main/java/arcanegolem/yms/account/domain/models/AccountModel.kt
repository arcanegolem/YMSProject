package arcanegolem.yms.account.domain.models

// Изменится в будущем на модель с историей изменений для графика
data class AccountModel(
  val name : String,
  val balance : String,
  val currency : String
)