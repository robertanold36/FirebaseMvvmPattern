package com.example.newsapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentImageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_image.*
import java.util.*
import kotlin.collections.HashMap


class UploadDetailFragment : Fragment() {

    companion object {


        private lateinit var storage: FirebaseStorage
        private lateinit var firestore:FirebaseFirestore
        private lateinit var storageReference: StorageReference
        const val SELECT_IMAGE_CODE = 100
        private lateinit var photoUri: Uri
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentImageBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_image, container, false)

        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference



        binding.finish.setOnClickListener {

            uploadImage()

        }

        binding.back.setOnClickListener {
            it.findNavController().navigate(R.id.action_imageFragment_to_contentFragment)

        }

        binding.choose.setOnClickListener {

            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it,
                    SELECT_IMAGE_CODE
                )
            }
        }

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_IMAGE_CODE) {

            if (data != null) {
                photoUri = data.data!!
            }


            selectedImage.setImageURI(photoUri)


        }
    }

    private fun uploadImage() {


        when (selectedImage.drawable) {
            null -> {
                Toast.makeText(activity,"please insert an image",Toast.LENGTH_SHORT).show()
            }
            else -> {

                if (isConnected()) {

                    try {
                        progressBar.visibility = View.VISIBLE
                        val fileName = UUID.randomUUID().toString()
                        val ref = FirebaseStorage.getInstance().getReference("/image/$fileName")
                        ref.putFile(photoUri).addOnSuccessListener {

                            ref.downloadUrl.addOnSuccessListener {
                                uploadData(it.toString())
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    activity,
                                    "your data is sent successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }
                        }
                    } catch (e: Exception) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity, "Network error ", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    Toast.makeText(activity, "No internet!!! Try Connecting Internet", Toast.LENGTH_SHORT).show()

                }
            }

        }

    }

    private fun uploadData(profileUrl:String){
        val description=editText.text.toString().trim()
        if(description.isEmpty()){
            editText.error="set data"
        }

        else {
            val userId = UUID.randomUUID().toString()
            val items = HashMap<String, Any>()
            items["description"] = description
            items["imageUrl"] = profileUrl
            firestore = FirebaseFirestore.getInstance()
            firestore.collection("data").document(userId).set(items).addOnSuccessListener {
                //
            }.addOnFailureListener {
                Toast.makeText(activity, "data fail to sent", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isConnected():Boolean{

        val connectivityManager= activity?.getSystemService(Context.CONNECTIVITY_SERVICE)

        return if(connectivityManager is ConnectivityManager){
            val networkInfo:NetworkInfo?=connectivityManager.activeNetworkInfo

            networkInfo?.isConnected?:false
        }else false
    }
}
