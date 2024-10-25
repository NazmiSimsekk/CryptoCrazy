package com.simsek.cryptocrazytwo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simsek.cryptocrazytwo.model.CryptoListItem
import com.simsek.cryptocrazytwo.repository.CryptoRepository
import com.simsek.cryptocrazytwo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    //arama algoritması için oluşturduk
    //indirilen liste buraya kaydedilecek ve search bara bir şeyler yazılıp silindiğinde orjinal listeyi burdan çekeceğiz
    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true

    //ViewModel çağırıldığı gibi loadCryptos çağırılacak
    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String){
       val listToSearch = if (isSearchStarting){
           cryptoList.value
       }else{
           initialCryptoList
       }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            //girilen değere(query) göre arama yapıyoruz ignoreCase ise büyük küçük harf ayırt etmiyor
            val result = listToSearch.filter {
                it.currency.contains(query.trim(), ignoreCase = true)
            }

            if (isSearchStarting){
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }

            cryptoList.value = result
        }
    }

    //getCryptoList() hata veriyor çnkü fonksiyon suspend değil
    // fonksiyon suspend yapılabilir yada viewModelScope içerisinde getCryptoList çağırılabilir
    //ikisininde farklı bedelleri var

    fun loadCryptos() {

        viewModelScope.launch {
            isLoading.value = true

            val result = repository.getCryptoList()

            when(result){

                is Resource.Success ->{
                    val cryptoItems = result.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency,cryptoListItem.price)
                    } as List<CryptoListItem>
                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }

                is Resource.Error ->{
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> TODO()
            }
        }
    }
}