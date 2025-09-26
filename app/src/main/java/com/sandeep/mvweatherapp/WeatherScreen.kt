package com.sandeep.mvweatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sandeep.mvweatherapp.response.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

@Composable

fun WeatherScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.pexels2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    val client = remember {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
    }
    val repository = remember { WeatherGetRequest(client) }
    val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory(repository))

    var city by remember { mutableStateOf("") }
    val weather by viewModel.weather.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = city,
                onValueChange = { city = it },
                placeholder = { Text("Enter a city") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(top = 50.dp, start = 15.dp, end = 5.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFc7ccd4))
            )
            //Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    viewModel.fetchWeather(city)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 50.dp, end = 15.dp),
                ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        weather?.let { DisplayWeather(it) }
    }
    LoaderOverlay(isLoading)
}
@Composable
fun DisplayWeather(weather: WeatherResponse) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\uD83C\uDFD9\uFE0F ${weather.location.name} (${weather.location.country})",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "\uD83C\uDF21\uFE0F Temperature:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${weather.current.temp_c}°C",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier

        ) {
            Row {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color(0xAAd8e2f2), RoundedCornerShape(10.dp))
                        .height(150.dp)
                        .width(150.dp),

                    ) {

                    Text(
                        "Condition",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("☀\uFE0F", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        weather.current.condition.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color(0xAAd8e2f2), RoundedCornerShape(10.dp))
                        .height(150.dp)
                        .width(150.dp),

                    ) {

                    Text(
                        "Cloud",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("\uD83C\uDF25\uFE0F", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "${weather.current.cloud}%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color(0xAAd8e2f2), RoundedCornerShape(10.dp))
                        .height(150.dp)
                        .width(150.dp),

                    ) {

                    Text(
                        "Humidity",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("\uD83D\uDCA7", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "${weather.current.humidity}%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color(0xAAd8e2f2), RoundedCornerShape(10.dp))
                        .height(150.dp)
                        .width(150.dp),

                    ) {
                    Text(
                        "Wind Speed",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("\uD83D\uDCA8", fontSize = 24.sp)

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "${weather.current.wind_kph} km/h",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2775f2),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LoaderOverlay(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)), // Transparent background
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
}
