package com.julie.reducecarbonfootprintapp.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import org.json.JSONObject
import java.io.Serializable

class Post(
    documentId: String,
    userId: String,
    title: String,
    content: String,
    upvotes: Int,
    downvotes: Int,
    createdAt: String
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
           field=value
        }
    var content: String = content
        get() = if (field.isEmpty()) "" else field
        set(value) {
            field=value
        }
    var upvotes: Int = upvotes
        get() = field
        set(value) {
            field=value
        }
    var downvotes: Int = downvotes
        get() = field
        set(value) {
            field=value
        }
    var userId: String = userId
        get() = field
        set(value) {
            field=value
        }
    var createdAt: String = createdAt
        get() = field
        set(value) {
            field=value
        }

    constructor() : this("", "", "", "", -1, -1,"") {

    }

    fun parseJson(): String {
        return Gson().toJson(this)
    }

    override fun create(): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("posts").document().set(this)
//            .addOnSuccessListener { println("Successful") }
//            .addOnFailureListener { e -> println(e.localizedMessage) }
    }

    override fun readOne(documentId: String): Task<DocumentSnapshot> {

        val db = FirebaseConnection().db;
        return db.collection("posts").document(documentId).get()
//            .addOnSuccessListener { document ->
//                try {
//                    if (document != null) {
//                        val extractedPost =
//                            Gson().fromJson(JSONObject(document.data).toString(), Post::class.java)
//                        println(extractedPost.parseJson())
//                    } else {
//                        println("Not found")
//                    }
//                } catch (ex: Exception) {
//                    println(ex.localizedMessage)
//                }
//            }.addOnFailureListener { e ->
//                println(e.localizedMessage)
//            }

    }


    override fun readAll(): Task<QuerySnapshot> {
        val db = FirebaseConnection().db;
//        db.collection("posts").orderBy("upvotes").get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val extractedPost =
//                        Gson().fromJson(JSONObject(document.data).toString(), Post::class.java)
//                    list.add(extractedPost)
//
//                    //println(list.size)
//                }
//            }
//            .addOnFailureListener { exception ->
//                println(exception.localizedMessage)
//            }

        return db.collection("posts").orderBy("upvotes").get()
    }

    override fun update(documentId: String, updatedDocument: Any): Task<Void> {
        val db = FirebaseConnection().db;
        return db.collection("posts").document(documentId).set(updatedDocument)
//            .addOnSuccessListener { println("Successfully updated") }
//            .addOnFailureListener { e -> println(e.localizedMessage) }
    }

    override fun delete(obj: Any) {
        TODO("Not yet implemented")
    }
}