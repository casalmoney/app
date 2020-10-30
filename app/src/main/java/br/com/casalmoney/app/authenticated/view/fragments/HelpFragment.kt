package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.casalmoney.app.R
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

    fun presentChat(view: View) {
        findNavController().navigate(R.id.action_helpFragment_to_chatFragment)
    }
}