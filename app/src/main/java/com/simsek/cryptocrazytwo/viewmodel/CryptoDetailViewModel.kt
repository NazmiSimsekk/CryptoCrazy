package com.simsek.cryptocrazytwo.viewmodel

import androidx.lifecycle.ViewModel
import com.simsek.cryptocrazytwo.model.Crypto
import com.simsek.cryptocrazytwo.repository.CryptoRepository
import com.simsek.cryptocrazytwo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel() {

    suspend fun getCrypto(): Resource<Crypto> {
        return repository.getCrypto()
    }
}