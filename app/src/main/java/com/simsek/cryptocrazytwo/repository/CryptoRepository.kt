package com.simsek.cryptocrazytwo.repository

import com.simsek.cryptocrazytwo.model.Crypto
import com.simsek.cryptocrazytwo.model.CryptoList
import com.simsek.cryptocrazytwo.service.CryptoAPI
import com.simsek.cryptocrazytwo.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

//Yapacağım işlerin fonksiyonlarını yazacağım sınıf

//Bu scope, tek acitivity miz olduğundan ötürü bu scopu kullandık
//activity çalışmayı durdurana kadar çalışmaya devam eder
//Modül içerisinde CryptoAPI döndüren bir fonksiyon yazdıysam bana heryerde CryptoAPI verilecektir
@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoAPI
){
    suspend fun getCryptoList(): Resource<CryptoList>{
        val response = try {
            api.getCryptoList()
        }catch (e: Exception){
            return Resource.Error("CryptoRepository getCryptoList: ${e.localizedMessage}")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(): Resource<Crypto> {
        val response = try {
            api.getCrypto()
        }catch (e: Exception){
            return Resource.Error("CryptoRepository getCrypto: ${e.localizedMessage}")
        }
        return Resource.Success(response)
    }
}