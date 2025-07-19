package arcanegolem.yms.transactions.domain.models

import arcanegolem.yms.categories.domain.models.CategoryModel

data class CategoryTotalModel(
  val total : String,
  val percentage : String,
  val category : CategoryModel
)
