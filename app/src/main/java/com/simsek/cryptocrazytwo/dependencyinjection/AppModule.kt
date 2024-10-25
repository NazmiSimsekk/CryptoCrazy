package com.simsek.cryptocrazytwo.dependencyinjection

import com.simsek.cryptocrazytwo.repository.CryptoRepository
import com.simsek.cryptocrazytwo.service.CryptoAPI
import com.simsek.cryptocrazytwo.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Retrofit repository gibi başka yerlere inject etmek için provide ettiğimiz sınıf
@Module
//Bu AppModule un singleton olacağını, app çalışırken tek bir şekilde burdan erişileceğini belirtiyoruz
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(
        api: CryptoAPI
    ) = CryptoRepository(api)

    //Bunu istediğimiz yerde inject edip retrofiti istediğimiz yerde kullanabiliriz
    @Singleton
    @Provides
    fun provideCryptoApi() : CryptoAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }
}