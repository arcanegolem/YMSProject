package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.TransactionsTotaledModel

interface TransactionsRepository {
  suspend fun loadExpenses(
    accountId : Int,
    currency: String,
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel
  suspend fun loadIncomes(
    accountId : Int,
    currency: String,
    periodStartMillis: Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel
}