package com.jetsada.Retofit.api

import com.jetsada.Retofit.model.Post
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

//    @Headers(
//        "Authorization: 123123123",
//        "Platform: Android"
//    )
    @GET("posts/1")
    suspend fun getPost(@Header("Auth") auth: String): Response<Post>

     //post/{value}
     @GET("posts/{postNumber}")
     suspend fun getPost2(
         @Path("postNumber") number: Int
     ): Response<Post>

    //post?{parameter}={value}
    @GET("posts")
    suspend fun getCustomPosts(
        @Query("userId") userId: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Response<List<Post>>

    @GET("posts")
    suspend fun getCustomPosts2(
        @Query("userId") userId: Int,
        @QueryMap options: Map<String, String>
    ): Response<List<Post>>

    //post Json
    @POST("posts")
    suspend fun pushPost(
        @Body post: Post
    ): Response<Post>

    //post (FormUrlEncoded)
    @FormUrlEncoded
    @POST("posts")
    suspend fun pushPost2(
        @Field("userId") userId: Int,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Response<Post>
}

