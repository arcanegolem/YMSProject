package arcanegolem.yms.account.di

import arcanegolem.yms.account.domain.repos.AccountRepository

interface AccountDependencies {
  fun resolveAccountRepository() : AccountRepository
}