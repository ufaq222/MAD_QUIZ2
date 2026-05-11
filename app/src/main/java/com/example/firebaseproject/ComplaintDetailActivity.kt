package com.example.firebaseproject

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class ComplaintDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvStudent: TextView
    private lateinit var tvRoll: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvPriority: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvDate: TextView
    private lateinit var btnBack: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_detail)

        tvTitle = findViewById(R.id.tvDetailTitle)
        tvStudent = findViewById(R.id.tvDetailStudent)
        tvRoll = findViewById(R.id.tvDetailRoll)
        tvCategory = findViewById(R.id.tvDetailCategory)
        tvPriority = findViewById(R.id.tvDetailPriority)
        tvDesc = findViewById(R.id.tvDetailDesc)
        tvStatus = findViewById(R.id.tvDetailStatus)
        tvDate = findViewById(R.id.tvDetailDate)
        btnBack = findViewById(R.id.btnBack)

        val complaintId = intent.getStringExtra("complaint_id") ?: return

        db = FirebaseFirestore.getInstance()
        db.collection("complaints").document(complaintId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val complaint = document.toObject(Complaint::class.java)
                    complaint?.let {
                        tvTitle.text = it.title
                        tvStudent.text = it.studentName
                        tvRoll.text = it.rollNumber
                        tvCategory.text = it.category
                        tvPriority.text = it.priority
                        tvDesc.text = it.description
                        tvStatus.text = it.status
                        
                        val date = Date(it.timestamp)
                        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        tvDate.text = format.format(date)
                    }
                }
            }

        btnBack.setOnClickListener { finish() }
    }
}
