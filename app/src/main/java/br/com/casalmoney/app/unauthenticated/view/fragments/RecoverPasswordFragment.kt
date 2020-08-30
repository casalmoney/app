package br.com.casalmoney.app.unauthenticated.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.databinding.FragmentRecoverPasswordBinding
import br.com.casalmoney.app.unauthenticated.viewmodel.RecoverPasswordViewModel

class RecoverPasswordFragment : Fragment() {

    private lateinit var binding: FragmentRecoverPasswordBinding

    private val viewModel: RecoverPasswordViewModel by lazy {
        ViewModelProvider(this).get(RecoverPasswordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecoverPasswordBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    fun recoverPassword(view: View) {
        findNavController().popBackStack()
    }

}