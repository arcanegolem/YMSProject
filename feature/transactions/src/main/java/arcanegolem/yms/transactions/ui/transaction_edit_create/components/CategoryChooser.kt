package arcanegolem.yms.transactions.ui.transaction_edit_create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.categories.domain.models.CategoryModel
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.core.utils.isEmoji
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChooser(
  isShown : Boolean,
  onDismissRequest : () -> Unit,
  onCategorySelected : (CategoryModel) -> Unit,
  models : List<CategoryModel>
) {
  val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val sheetScope = rememberCoroutineScope()

  val onDismissLocal : () -> Unit = {
    sheetScope.launch {
      state.hide()
    }.invokeOnCompletion { onDismissRequest() }
  }

  if (isShown) {
    ModalBottomSheet(
      sheetState = state,
      onDismissRequest = onDismissLocal
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(0.6f)
      ) {
        items(
          items = models,
          key = { it.toString() }
        ) { category ->
          YMSListItem(
            modifier = Modifier
              .fillMaxWidth()
              .height(70.dp)
              .clickable {
                onCategorySelected(category)
                onDismissLocal()
              },
            lead = {
              if (category.emoji.isNotEmpty()) {
                Box(
                  modifier = Modifier.size(24.dp)
                ) {
                  val isEmoji = remember(category.emoji) { category.emoji.isEmoji() }
                  Box(
                    modifier = Modifier
                      .fillMaxSize()
                      .clip(CircleShape)
                      .background(MaterialTheme.colorScheme.secondary)
                  )
                  Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = category.emoji,
                    fontSize = if (isEmoji) 18.sp else 10.sp,
                    lineHeight = if (isEmoji) 24.sp else 22.sp
                  )
                }
              }
            },
            content = {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column {
                  Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }
              }
            }
          )
        }
      }
    }
  }
}