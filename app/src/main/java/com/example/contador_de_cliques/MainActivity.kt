package com.example.contador_de_cliques

import DataStoreManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.contador_de_cliques.ui.theme.Contador_de_cliquesTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val datastore = DataStoreManager(applicationContext)
        setContent {
            Contador_de_cliquesTheme {
                ContadorDeCliques(datastore)
            }
        }
    }
}

@Composable
fun ContadorDeCliques(dataStore: DataStoreManager) {
    val scope = rememberCoroutineScope()
    val contador by dataStore.contador.collectAsState(initial = 0)
    val dataUltimoClique by dataStore.dataUltimoClique.collectAsState(initial = "Nunca")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Você clicou $contador vezes")
        Text(text = "Último clique: $dataUltimoClique", style = MaterialTheme.typography.bodySmall)

        Row {
            Button(
                onClick = {
                    val dataAtual = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
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
}

