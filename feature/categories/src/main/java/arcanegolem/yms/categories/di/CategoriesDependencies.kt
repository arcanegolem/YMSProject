package arcanegolem.yms.categories.di

import arcanegolem.yms.categories.domain.repos.CategoriesRepository

interface CategoriesDependencies {
  fun resolveCategoriesRepository() : CategoriesRepository
}