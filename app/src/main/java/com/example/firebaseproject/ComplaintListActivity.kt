package com.example.firebaseproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ComplaintListActivity : AppCompatActivity() {

    private lateinit var rvComplaints: RecyclerView
    private lateinit var tvEmptyMessage: TextView
    private lateinit var db: FirebaseFirestore
    private val complaintList = mutableListOf<Complaint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_list)

        rvComplaints = findViewById(R.id.rvComplaints)
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)
        rvComplaints.layoutManager = LinearLayoutManager(this)

        db = FirebaseFirestore.getInstance()

        db.collection("complaints")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                complaintList.clear()
                if (snapshot != null) {
                    for (doc in snapshot.documents) {
                        val complaint = doc.toObject(Complaint::class.java)
                        if (complaint != null) {
                            complaintList.add(complaint)
                        }
                    }
                }

                if (complaintList.isEmpty()) {
                    tvEmptyMessage.visibility = View.VISIBLE
                    rvComplaints.visibility = View.GONE
                } else {
                    tvEmptyMessage.visibility = View.GONE
                    rvComplaints.visibility = View.VISIBLE
                    rvComplaints.adapter = ComplaintAdapter(complaintList) { complaint ->
                        val intent = Intent(this@ComplaintListActivity, ComplaintDetailActivity::class.java)
                        intent.putExtra("complaint_id", complaint.id)
                        startActivity(intent)
                    }
                }
            }
    }
}
