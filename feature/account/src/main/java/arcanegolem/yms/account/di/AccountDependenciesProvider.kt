package arcanegolem.yms.account.di

interface AccountDependenciesProvider {
  fun resolveAccountDependencies() : AccountDependencies
}