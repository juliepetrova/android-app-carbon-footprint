package com.julie.reducecarbonfootprintapp.models

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseConnection() {
    var db:FirebaseFirestore = FirebaseFirestore.getInstance()

}