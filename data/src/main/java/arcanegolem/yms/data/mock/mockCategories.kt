package arcanegolem.yms.data.mock

import arcanegolem.yms.data.remote.models.Category

internal val mockCategories = listOf(
  Category(
    id = 1,
    name = "Аренда квартиры",
    emoji = "\uD83C\uDFE1",
    isIncome = false
  ),
  Category(
    id = 2,
    name = "Одежда",
    emoji = "\uD83D\uDC57",
    isIncome = false
  ),
  Category(
    id = 3,
    name = "На собачку",
    emoji = "\uD83D\uDC36",
    isIncome = false
  ),
  Category(
    id = 4,
    name = "Ремонт квартиры",
    emoji = "РК",
    isIncome = false
  ),
  Category(
    id = 5,
    name = "Продукты",
    emoji = "\uD83C\uDF6D",
    isIncome = false
  ),
  Category(
    id = 6,
    name = "Спортзал",
    emoji = "\uD83C\uDFCB\uFE0F",
    isIncome = false
  ),
  Category(
    id = 7,
    name = "Медицина",
    emoji = "\uD83D\uDC8A",
    isIncome = false
  )
)