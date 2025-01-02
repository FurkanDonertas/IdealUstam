package com.furkandonertas.idealustam.features.onboarding.masters.data.datasource.remote

import com.furkandonertas.idealustam.features.onboarding.masters.data.model.response.MasterResponse
import retrofit2.Response
import retrofit2.http.*

interface MasterApiService {
    @GET("masters")
    suspend fun getMasters(): Response<List<MasterResponse>>

    @GET("masters/{id}")
    suspend fun getMasterById(@Path("id") id: String): Response<MasterResponse>

    @GET("masters/search")
    suspend fun searchMasters(@Query("query") query: String): Response<List<MasterResponse>>

    @GET("masters/specialty/{specialty}")
    suspend fun getMastersBySpecialty(@Path("specialty") specialty: String): Response<List<MasterResponse>>

    @POST("masters/{masterId}/favorite")
    suspend fun toggleFavoriteMaster(@Path("masterId") masterId: Int): Response<Unit>
} 