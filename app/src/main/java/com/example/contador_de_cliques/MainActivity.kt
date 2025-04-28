package com.example.contador_de_cliques

import DataStoreManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contador_de_cliques.ui.theme.Contador_de_cliquesTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val datastore = DataStoreManager(applicationContext)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            Contador_de_cliquesTheme(darkTheme = isDarkTheme) {
                ContadorDeCliques(dataStore = datastore,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme} )
            }
        }
    }
}

@Composable
fun ContadorDeCliques(dataStore: DataStoreManager,
                      isDarkTheme: Boolean,
                      onToggleTheme: () -> Unit) {
    val scope = rememberCoroutineScope()
    val contador by dataStore.contador.collectAsState(initial = 0)
    val dataUltimoClique by dataStore.dataUltimoClique.collectAsState(initial = "Nunca")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { onToggleTheme() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "Trocar tema",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(text = "Você clicou $contador vezes",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onBackground
            )


            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // Espaça os botões
                modifier = Modifier.fillMaxWidth() // Faz com que os botões ocupem a largura total
            ) {
                Button(
                    onClick = {
                        val dataAtual = SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss",
                            Locale.getDefault()
                        ).format(Date())
                        scope.launch {
                            dataStore.salvarContador(contador + 1)
                            dataStore.salvarDataHora(dataAtual)
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Clique aqui")
                }

                Button(onClick = {
                    scope.launch {
                        dataStore.salvarContador(0)
                    }
                }) {
                    Text("Resetar")
                }
            }
        }

        Text(
            text = "Último clique: $dataUltimoClique",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Centraliza na parte inferior
                .padding(16.dp)
        )
    }
}


