package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.casalmoney.app.authenticated.view.adapters.ChatAdapter
import br.com.casalmoney.app.authenticated.viewModel.ChatViewModel
import br.com.casalmoney.app.databinding.FragmentChatBinding
import br.com.casalmoney.app.utils.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    private val viewModel: ChatViewModel by lazy {
        ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    private lateinit var listView: ListView
    private val progressDialog = CustomProgressDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
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