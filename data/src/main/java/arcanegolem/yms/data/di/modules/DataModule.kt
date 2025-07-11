package arcanegolem.yms.data.di.modules

import dagger.Module

@Module(
  includes = [NetworkModule::class, RepositoryBindingModule::class, StorageModule::class]
)
interface DataModule