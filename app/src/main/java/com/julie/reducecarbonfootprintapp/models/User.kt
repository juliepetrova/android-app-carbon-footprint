package com.julie.reducecarbonfootprintapp.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import java.io.Serializable

class User(
    city: String,
    firstName: String,
    lastName: String,
    points: Int,
    completedSuggestions: ArrayList<String>,
    upvotedPostIds: ArrayList<String>
): Serializable, Model() {
    
    constructor() : this("", "", "", 0, ArrayList<String>(), ArrayList<String>()) {

    }
    constructor(city: String, firstName: String, lastName: String) : this("", "", "", 0, ArrayList<String>(), ArrayList<String>()) {

    }

    var city: String = city
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field = city
        }
    var firstName: String = firstName
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field = firstName
        }
    var lastName: String = lastName
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field = value
        }
    var points: Int = points
        get() = if (field < 0) 0 else field
        set(value) {
            field = value
        }

    var completedSuggestions: MutableList<String> = completedSuggestions
        get() = field
        set(value) {
            field = value
        }



    fun parseJson(): String {
        return Gson().toJson(this)
    }

    override fun create(): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("users").document().set(this)
//            .addOnSuccessListener { println("Successful") }
//            .addOnFailureListener { e -> println(e.localizedMessage) }
    }

    override fun readOne(documentId: String): Task<DocumentSnapshot> {
        val db = FirebaseConnection().db;
        return db.collection("users").document(documentId).get()    }

    override fun readAll(): Task<QuerySnapshot> {
        val db = FirebaseConnection().db;
        return db.collection("users").orderBy("points",Query.Direction.DESCENDING).get()
    }

    override fun update(documentId: String, updatedDocument: Any): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("users").document(documentId).set(updatedDocument)
    }

    override fun delete(obj: Any) {
        TODO("Not yet implemented")
    }
}