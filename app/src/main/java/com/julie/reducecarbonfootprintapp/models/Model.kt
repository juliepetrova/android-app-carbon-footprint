package com.julie.reducecarbonfootprintapp.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

abstract class Model {
    abstract fun create(): Task<Void> ;
    abstract fun readOne(documentId: String): Task<DocumentSnapshot>;
    abstract fun readAll(): Task<QuerySnapshot>
    abstract fun update(documentId:String,updatedDocument:Any): Task<Void>;
    abstract fun delete(obj: Any):Unit;
}