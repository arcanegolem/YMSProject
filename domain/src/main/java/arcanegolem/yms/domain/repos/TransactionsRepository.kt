package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.TransactionsTotaledModel

interface TransactionsRepository {
  suspend fun loadExpenses(
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel
  suspend fun loadIncomes(
    periodStartMillis: Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel
}