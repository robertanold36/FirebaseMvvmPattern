package com.example.newsapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentContentBinding


class MiddleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentContentBinding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_content,container,false)


       binding.next.setOnClickListener {
           it.findNavController().navigate(R.id.action_contentFragment_to_imageFragment)
       }

        return binding.root
    }



}
