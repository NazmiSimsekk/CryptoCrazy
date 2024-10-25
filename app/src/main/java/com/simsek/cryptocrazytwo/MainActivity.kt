package com.simsek.cryptocrazytwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simsek.cryptocrazytwo.ui.theme.CryptoCrazyTwoTheme
import com.simsek.cryptocrazytwo.view.CryptoDetailScreen
import com.simsek.cryptocrazytwo.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint

//Hilte uygulamanın nerden başlayacağını bildirdik
//hilt ile ilgili bir işlem daha var oda boş bir class açacağız
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoCrazyTwoTheme {
                //Compose recomposition araştır
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "crypto_list_screen"){

                    //route dediği hangi id ye gidileceği
                    composable("crypto_list_screen"){
                        //CryptoListScreen
                        CryptoListScreen(navController = navController)
                    }

                    composable("crypto_detail_screen/{cryptoId}/{cryptoPrice}", arguments = listOf(
                        navArgument("cryptoId"){
                            type = NavType.StringType
                        },
                        navArgument("cryptoPrice"){
                            type = NavType.StringType
                        }
                    )){
                        //CryptoDetailScreen

                        val cryptoId = remember {
                            it.arguments?.getString("cryptoID")
                        }

                        val cryptoPrice = remember {
                            it.arguments?.getString("cryptoPrice")
                        }

                        CryptoDetailScreen(id = cryptoId ?: "", price = cryptoPrice ?: "", navController = navController)
                    }
                }
            }
        }
    }
}
