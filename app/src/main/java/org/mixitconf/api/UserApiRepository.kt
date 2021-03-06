package org.mixitconf.api

import org.mixitconf.api.dto.UserDto
import org.mixitconf.api.dto.WebsiteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiRepository {

    /**
     * Send a request to send a token to a user by email. When a user want to refresh his data this token is required. This request can respond with
     * <ul>
     *     <li>200 : {"message":"A token was send by email. Please check your mailbox and send it in the future request"}</li>
     *     <li>400 : {"message":"This email is not known. You have to create an account on our website if you want to use this functionnality"}</li>
     *     <li>400 : {"message":"Credentials are invalids"}</li>
     *     <li>500 : {"message":"An expected error occured on email sent"}</li>
     * </ul>
     */
    @GET("token")
    suspend fun askForToken(@Query("email") email: String): Response<WebsiteResponse>

    /**
     * Check if a token is valid (used before a askForToken)
     * <ul>
     *     <li>200 : {"message":"Credentials are valids"}</li>
     *     <li>400 : {"message":"Credentials are invalids"}</li>
     * </ul>
     */
    @GET("token/check")
    suspend fun checkToken(
        @Query("email") email: String,
        @Query("token") token: String
    ): Response<WebsiteResponse>


    /**
     * Read user profile
     * <ul>
     *     <li>200 : a user</li>
     *     <li>400 : {"message":"Credentials are invalids"}</li>
     * </ul>
     */
    @GET("me")
    suspend fun profile(@Query("email") email: String,
                @Query("token") token: String): Response<UserDto>

}