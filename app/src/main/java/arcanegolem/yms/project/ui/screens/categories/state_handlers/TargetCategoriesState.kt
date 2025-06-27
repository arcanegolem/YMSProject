package arcanegolem.yms.project.ui.screens.categories.state_handlers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.screens.categories.CategoriesState
import arcanegolem.yms.project.ui.components.YMSListItem
import arcanegolem.yms.project.util.isEmoji

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetCategoriesState(state : CategoriesState.Target) {
  Box(modifier = Modifier.fillMaxSize()){
    Column {
      var query by remember { mutableStateOf("") }

      DockedSearchBar (
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        inputField = {
          SearchBarDefaults.InputField(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onSearch = {},
            onQueryChange = { query = it },
            expanded = false,
            onExpandedChange = {},
            trailingIcon = {
              Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = null
              )
            },
            placeholder = {
              Text(
                text = stringResource(R.string.category_search_placeholder),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          )
        },
        expanded = false,
        onExpandedChange = {},
        content = {}
      )
      HorizontalDivider(thickness = 1.dp)
      LazyColumn {
        items(state.result, key = { it.toString() }) { category ->
          YMSListItem(
            modifier = Modifier
              .fillMaxWidth()
              .height(70.dp),
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
