package com.example.newcekirge.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.newcekirge.R
import com.example.newcekirge.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {
    private lateinit var binding:FragmentWebViewBinding
    private val args by navArgs<WebViewFragmentArgs>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentWebViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUniversity = args.currentUniversity
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = currentUniversity.name
        val webView = binding.webView

        webView.settings.javaScriptEnabled = true

        webView.loadUrl(args.currentUniversity.website) // İstediğiniz URL ile değiştirin
    }


}