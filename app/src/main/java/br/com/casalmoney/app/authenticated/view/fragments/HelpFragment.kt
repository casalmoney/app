package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.viewModel.HelpViewModel
import br.com.casalmoney.app.databinding.FragmentHelpBinding
import br.com.casalmoney.app.unauthenticated.view.adapter.ChatAdapter

class HelpFragment: Fragment() {
    private lateinit var binding: FragmentHelpBinding

    private val viewModel: HelpViewModel by lazy {
        ViewModelProvider(this).get(HelpViewModel::class.java)
    }

    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        setupListView(binding.root)

        return binding.root
    }

    private fun setupListView(view: View) {

        listView = view.findViewById(R.id.list_of_messages) as ListView

        val adapter = activity?.let { ChatAdapter(it, viewModel.messages) }
        if (adapter != null) {
            viewModel.adapter = adapter
        }
        listView.adapter = adapter
    }

    fun sendMessage(view: View) {
        viewModel.sendMessage()
    }

}