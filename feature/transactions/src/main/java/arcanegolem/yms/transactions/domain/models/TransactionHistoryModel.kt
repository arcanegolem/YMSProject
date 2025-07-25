package arcanegolem.yms.transactions.domain.models

import arcanegolem.yms.core.domain.models.TransactionsTotaledModel

data class TransactionHistoryModel(
  val isIncome : Boolean,
  val transactionsTotaled : TransactionsTotaledModel,
  val periodStart : Long,
  val periodEnd : Long
)
