package com.innovative.porosh.tourmate.data.model

/**
 * This class is not used yet
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)