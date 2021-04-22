package com.julie.reducecarbonfootprintapp.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.io.Serializable

class Goal(
    title: String,
    description: String,
    startDate: String,
    endDate: String,
    goalPeople: Int,
    participants: Int,
) : Serializable, Model() {

    var title: String = title
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field=value
        }
    var description: String = description
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field=value
        }

    var startDate: String = startDate
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field=value
        }
    var endDate: String = endDate
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field=value
        }
    var goalPeople: Int = goalPeople
        get() = field
        set(value) {
            field=value
        }
    var participants: Int = participants
        get() = field
        set(value) {
            field=value
        }


    constructor() : this("", "", "","",0, 0) {

    }


    override fun create(): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("goals").document().set(this)
    }

    override fun readOne(documentId: String): Task<DocumentSnapshot> {
        val db = FirebaseConnection().db;
        return db.collection("goals").document(documentId).get()
    }

    override fun readAll(): Task<QuerySnapshot> {
        val db = FirebaseConnection().db;
        return db.collection("goals").get()

    }

    override fun update(documentId: String, updatedDocument: Any): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("goals").document(documentId).set(updatedDocument)    }

    override fun delete(obj: Any) {
        TODO("Not yet implemented")
    }
}