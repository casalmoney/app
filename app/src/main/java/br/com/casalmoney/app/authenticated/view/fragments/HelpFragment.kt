package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.casalmoney.app.authenticated.viewModel.HelpViewModel
import br.com.casalmoney.app.databinding.FragmentHelpBinding
import br.com.casalmoney.app.authenticated.view.adapters.ChatAdapter
import br.com.casalmoney.app.utils.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class HelpFragment: Fragment() {
    private lateinit var binding: FragmentHelpBinding

    private val viewModel: HelpViewModel by lazy {
        ViewModelProvider(this).get(HelpViewModel::class.java)
    }

    private lateinit var listView: ListView
    private val progressDialog = CustomProgressDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoading()
        setupListView()
    }

    private fun setupLoading() {
        viewModel.disposable = viewModel.isLoading.subscribe { isLoading ->
            if (isLoading) {
                activity?.let { progressDialog.show(it) }
            } else {
                progressDialog.dialog.dismiss()
            }
        }
    }

    private fun setupListView() {
        listView = binding.listOfMessages

        viewModel.messageList.observe(viewLifecycleOwner, { list ->
            listView.adapter = ChatAdapter(list)
        })

        viewModel.getPreviousMessages()
    }

    fun sendMessage(view: View) {
        viewModel.sendMessage()
    }

}