package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import newsapp.composeapp.generated.resources.Res
import newsapp.composeapp.generated.resources.oldStandardTT_Bold
import newsapp.composeapp.generated.resources.oldStandardTT_Regular
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font



@Composable
fun getRegularFont(): FontFamily = FontFamily(Font(Res.font.oldStandardTT_Regular))

@Composable
fun getBoldFont(): FontFamily = FontFamily(Font(Res.font.oldStandardTT_Bold))