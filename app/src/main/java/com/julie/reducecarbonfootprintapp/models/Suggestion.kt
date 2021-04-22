package com.julie.reducecarbonfootprintapp.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import java.io.Serializable

class Suggestion(
    title: String,
    content: String,
    savedTonsPerYear: Double,
    points: Int,
    documentId: String = ""
) : Serializable, Model() {

    @Exclude
    var documentId: String = documentId
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field = value
        }

    var title: String = title
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field = value
        }
    var content: String = content
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field = value
        }
    var savedTonsPerYear: Double = savedTonsPerYear
        get() = if (field < 0) 0.0 else field
        set(value) {
            field = value
        }
    var points: Int = points
        get() = if (field < 0) 0 else field
        set(value) {
            field = value
        }


    constructor() : this("", "", 0.0, 0) {

    }

    fun parseJson(): String {
        return Gson().toJson(this)
    }

    override fun create(): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("suggestions").document().set(this)
//            .addOnSuccessListener { println("Successful") }
//            .addOnFailureListener { e -> println(e.localizedMessage) }
    }

    override fun readOne(documentId: String): Task<DocumentSnapshot> {

        val db = FirebaseConnection().db;
        return db.collection("suggestions").document(documentId).get()

    }


    override fun readAll(): Task<QuerySnapshot> {
        val db = FirebaseConnection().db;
        return db.collection("suggestions").get()

    }

    override fun update(documentId: String, updatedDocument: Any): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("suggestions").document(documentId).set(updatedDocument)
//            .addOnSuccessListener { println("Successfully updated") }
//            .addOnFailureListener { e -> println(e.localizedMessage) }
    }

    override fun delete(obj: Any) {
        TODO("Not yet implemented")
    }

}