package com.example.f_a_msg.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.f_a_msg.R
import com.example.f_a_msg.listener.FragmentChangeListener
import java.lang.Exception

class SecondTabFragment :Fragment(){

    private var listener: FragmentChangeListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FragmentChangeListener){
            listener = context
        }else{
            Throwable(Exception("cast field"))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_second, container, false)
        view.findViewById<Button>(R.id.secondBtn).setOnClickListener { listener?.information("second send") }
        return view
    }
}
