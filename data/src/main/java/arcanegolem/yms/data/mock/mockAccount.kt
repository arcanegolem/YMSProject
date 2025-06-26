package arcanegolem.yms.data.mock

import arcanegolem.yms.data.remote.models.AccountBrief

internal val mockAccount = AccountBrief(
  id = 1,
  name = "Основной счет",
  balance = "1000000.00",
  currency = "RUB"
)