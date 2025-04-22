package com.example.contador_de_cliques

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Contador_de_cliquesTheme {
                ContadorDeCliques()
            }
        }
    }
}

@Composable
fun ContadorDeCliques() {
    var contador by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Você clicou $contador vezes")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Espaço entre os botões
            verticalAlignment = Alignment.CenterVertically // Alinha os botões verticalmente no centro
        ) {
            Button(
                onClick = { contador++ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Clique aqui")
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espaço entre os botões

            // Botão para resetar o contador
            Button(
                onClick = { contador = 0 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(text = "Resetar contagem")
            }
        }
    }
}
