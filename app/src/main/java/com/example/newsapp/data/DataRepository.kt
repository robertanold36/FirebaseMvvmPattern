package com.example.newsapp.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import java.util.*
import kotlin.collections.HashMap

class DataRepository {

    private var dataSet:MutableLiveData<MutableList<MyData>> = MutableLiveData()


   private val fStore= FirebaseFirestore.getInstance()
   private val storage= FirebaseStorage.getInstance()
   private val userId=UUID.randomUUID().toString()
   private val  ref=storage.getReference("/image/$userId")

     fun getAllInfo(): LiveData<MutableList<MyData>> {

         val mutableList= mutableListOf<MyData>()
         fStore.collection("data").addSnapshotListener { querySnapshot, exception ->

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

                     dataSet.value = mutableList
                 } else {
                     //
                 }

             }
         }
         return dataSet

     }

    fun uploadData(imageUrl:Uri,description:String)=Completable.create {emitter->
            ref.putFile(imageUrl).addOnCompleteListener { imageUrl ->
                if(!emitter.isDisposed) {
                    if(imageUrl.isSuccessful) {
                        ref.downloadUrl.addOnSuccessListener {
                            val items = HashMap<String, Any>()
                            items["description"] = description
                            items["imageUrl"] = it.toString()

                            fStore.collection("data").document(userId).set(items)
                                .addOnSuccessListener {
                                    emitter.onComplete()

                                }.addOnFailureListener {

                            }
                        }
                    }

                    else{
                        emitter.onError(imageUrl.exception!!)
                    }
                }
        }

    }


}
