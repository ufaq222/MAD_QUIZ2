package com.example.firebaseproject

data class Complaint(
    val id: String? = null,
    val studentName: String? = null,
    val rollNumber: String? = null,
    val title: String? = null,
    val category: String? = null,
    val priority: String? = null,
    val description: String? = null,
    val status: String = "Pending",
    val timestamp: Long = System.currentTimeMillis()
)
