package arcanegolem.yms.transactions.ui.analysis.state_handlers

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSDatePicker
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.core.utils.toReadableDate
import arcanegolem.yms.graph.charts.pie.PieChart
import arcanegolem.yms.transactions.domain.models.toPieChartModel
import arcanegolem.yms.transactions.ui.analysis.AnalysisEvent
import arcanegolem.yms.transactions.ui.analysis.AnalysisState
import arcanegolem.yms.transactions.ui.analysis.components.SummarizedCategoryListItem
import arcanegolem.yms.transactions.ui.history.components.DatePickerSource

@Composable
fun TargetAnalysisState(
  state: AnalysisState.Target,
  eventProcessor : (AnalysisEvent) -> Unit
) {
  var datePickerActive by remember { mutableStateOf(false) }
  var datePickerSource by remember { mutableStateOf(DatePickerSource.PERIOD_START) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.onSecondary)
  ) {
    Column {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable {
            datePickerSource = DatePickerSource.PERIOD_START
            datePickerActive = true
          },
        content = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = stringResource(R.string.start_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(MaterialTheme.colorScheme.primary)
            ) {
              Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
                text = state.result.periodStart.toReadableDate(LocaleList.current.localeList[0].language),
                fontWeight = FontWeight.W500,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      )
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable {
            datePickerSource = DatePickerSource.PERIOD_END
            datePickerActive = true
          },
        content = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = stringResource(R.string.end_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(MaterialTheme.colorScheme.primary)
            ) {
              Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
                text = state.result.periodEnd.toReadableDate(LocaleList.current.localeList[0].language),
                fontWeight = FontWeight.W500,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      )
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        content = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = stringResource(R.string.total_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Text(
              text = state.result.total,
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      )
      
      if (state.result.categoriesData.isNotEmpty()){
        val chartModel = state.result.toPieChartModel(5)
        
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          PieChart(
            modifier = Modifier
              .size(185.dp),
            model = chartModel,
            radius = 70.dp
          )
          HorizontalDivider(thickness = 1.dp)
        }
      }
      
      LazyColumn {
        items(state.result.categoriesData, key = { it.toString() }) { categoryTotalModel ->
          SummarizedCategoryListItem(categoryTotalModel)
        }
      }
    }

    YMSDatePicker(
      isActive = datePickerActive,
      onDismissRequest = { datePickerActive = false },
      onDateSelected = { selectedDate ->
        if (selectedDate != null){
          eventProcessor(AnalysisEvent.ChangePeriodSideAndLoadData(datePickerSource, selectedDate))
        }
      },
      onDateClear = {
        eventProcessor(AnalysisEvent.ChangePeriodSideAndLoadData(datePickerSource, null))
      },
      initialDateMillis = when (datePickerSource) {
        DatePickerSource.PERIOD_START -> state.result.periodStart
        DatePickerSource.PERIOD_END -> state.result.periodEnd
      }
    )
  }
}