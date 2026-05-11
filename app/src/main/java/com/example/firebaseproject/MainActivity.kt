package com.example.firebaseproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var etStudentName: EditText
    private lateinit var etRollNumber: EditText
    private lateinit var etComplaintTitle: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerPriority: Spinner
    private lateinit var etDescription: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnViewComplaints: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Find the root view
        val mainView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        
        // Handle window insets while preserving the original XML padding
        val originalPaddingLeft = mainView.paddingLeft
        val originalPaddingTop = mainView.paddingTop
        val originalPaddingRight = mainView.paddingRight
        val originalPaddingBottom = mainView.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                originalPaddingLeft + systemBars.left,
                originalPaddingTop + systemBars.top,
                originalPaddingRight + systemBars.right,
                originalPaddingBottom + systemBars.bottom
            )
            insets
        }

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Initialize UI elements
        etStudentName = findViewById(R.id.etStudentName)
        etRollNumber = findViewById(R.id.etRollNumber)
        etComplaintTitle = findViewById(R.id.etComplaintTitle)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerPriority = findViewById(R.id.spinnerPriority)
        etDescription = findViewById(R.id.etDescription)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnViewComplaints = findViewById(R.id.btnViewComplaints)

        btnSubmit.setOnClickListener {
            submitComplaint()
        }

        btnViewComplaints.setOnClickListener {
            startActivity(Intent(this, ComplaintListActivity::class.java))
        }
    }

    private fun submitComplaint() {
        val name = etStudentName.text.toString().trim()
        val roll = etRollNumber.text.toString().trim()
        val title = etComplaintTitle.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()
        val priority = spinnerPriority.selectedItem.toString()
        val desc = etDescription.text.toString().trim()

        if (name.isEmpty() || roll.isEmpty() || title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val complaintRef = db.collection("complaints").document()
        val id = complaintRef.id
        val complaint = Complaint(id, name, roll, title, category, priority, desc)

        complaintRef.set(complaint)
            .addOnSuccessListener {
                Toast.makeText(this, "Complaint Submitted Successfully!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        etStudentName.text.clear()
        etRollNumber.text.clear()
        etComplaintTitle.text.clear()
        etDescription.text.clear()
        spinnerCategory.setSelection(0)
        spinnerPriority.setSelection(0)
    }
}
