package com.kodiiiofc.urbanuniversity.jetpackcompose.sliderswitch

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodiiiofc.urbanuniversity.jetpackcompose.sliderswitch.ui.theme.SliderSwitchTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var isButtonEnabled by remember {
                mutableStateOf(false)
            }

            var data by remember {
                mutableStateOf("")
            }

            val isDataLoaded by remember {
                derivedStateOf {
                    data.isNotBlank()
                }
            }

            val coroutineScope = rememberCoroutineScope()

            SliderSwitchTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            if (isDataLoaded) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            ParagraphStyle(
                                                textAlign = TextAlign.Justify,
                                                lineHeight = 24.sp,
                                                textIndent = TextIndent(10.sp, 0.sp)
                                            )
                                        ) {
                                            append(data)
                                        }
                                    },
                                    fontSize = 16.sp
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Switch(
                                checked = isButtonEnabled,
                                onCheckedChange = { isButtonEnabled = it }
                            )

                            Button(
                                onClick = {
                                   coroutineScope.launch {
                                       Toast.makeText(
                                           this@MainActivity,
                                           "Загрузка данных",
                                           Toast.LENGTH_SHORT
                                       ).show()

                                       delay(2000)
                                       data = loadData()

                                       Toast.makeText(
                                           this@MainActivity,
                                           "Данные загружены",
                                           Toast.LENGTH_LONG
                                       ).show()

                                   }
                                },
                                enabled = isButtonEnabled
                            ) {
                                Text("Загрузить данные")
                            }

                        }
                    }

                }
            }
        }
    }

    private fun loadData(): String {
        return resources.getString(R.string.lorem)
    }
}