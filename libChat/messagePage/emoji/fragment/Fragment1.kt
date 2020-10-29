package com.somoplay.wefungame.libChat.messagePage.emoji.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.somoplay.wefungame.R
import java.security.acl.LastOwnerException

class Fragment1: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var rootView: View = LayoutInflater.from(activity).inflate(R.layout.fragment1, null)
        var tv: TextView = rootView.findViewById(R.id.tv) as TextView

        tv.setText(args.getString("Interge"))
        return rootView
    }
}