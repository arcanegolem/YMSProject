package arcanegolem.yms.data.mock

import arcanegolem.yms.data.remote.models.Category
import arcanegolem.yms.data.remote.models.TransactionResponse

val mockTransactions = listOf(
  TransactionResponse(
    id = 1,
    account = mockAccount,
    category = mockCategories[0],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 2,
    account = mockAccount,
    category = mockCategories[1],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 3,
    account = mockAccount,
    category = mockCategories[2],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = "Джек",
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 4,
    account = mockAccount,
    category = mockCategories[2],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = "Энни",
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 5,
    account = mockAccount,
    category = mockCategories[3],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 6,
    account = mockAccount,
    category = mockCategories[4],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 7,
    account = mockAccount,
    category = mockCategories[5],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 8,
    account = mockAccount,
    category = mockCategories[6],
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 9,
    account = mockAccount,
    category = Category(
      id = 8,
      name = "Зарплата",
      emoji = "",
      isIncome = true
    ),
    amount = "500000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  ),
  TransactionResponse(
    id = 10,
    account = mockAccount,
    category = Category(
      id = 9,
      name = "Подработка",
      emoji = "",
      isIncome = true
    ),
    amount = "100000.00",
    transactionDate = "2025-06-10T10:47:45.759Z",
    comment = null,
    createdAt = "2025-06-10T10:47:45.759Z",
    updatedAt = "2025-06-10T10:47:45.759Z"
  )
)