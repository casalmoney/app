package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.casalmoney.app.authenticated.viewModel.StatementViewModel
import br.com.casalmoney.app.databinding.FragmentStatementBinding

class StatementFragment: Fragment() {

    private lateinit var binding: FragmentStatementBinding

    private val viewModel: StatementViewModel by lazy {
        ViewModelProvider(this).get(StatementViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatementBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }
}