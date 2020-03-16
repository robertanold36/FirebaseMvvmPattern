package com.example.newsapp.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class DataRepository {

    var myDatas:MutableLiveData<MutableList<MyData>> = MutableLiveData()

    lateinit var firebasestore: FirebaseFirestore

     fun getAllInfo(): LiveData<MutableList<MyData>> {

          val mutableList= mutableListOf<MyData>()
         firebasestore= FirebaseFirestore.getInstance()
         firebasestore.collection("data").addSnapshotListener { querySnapshot, exception ->

             if (exception != null) {

                 //

             } else {

                 if (querySnapshot != null) {
                     for (document in querySnapshot.documents) {
                         val imageUrl: String? = document.getString("imageUrl")
                         val description: String? = document.getString("description")
                         val myData =
                             MyData(
                                 imageUrl!!,
                                 description!!
                             )
                         mutableList.add(myData)
                     }

                     myDatas.value = mutableList
                 } else {
                     //
                 }

             }
         }
         return myDatas

     }

}
