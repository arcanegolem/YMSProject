package arcanegolem.yms.transactions.di

interface TransactionsDependenciesProvider {
  fun resolveTransactionsDependencies() : TransactionsDependencies
}