package br.com.casalmoney.app.unauthenticated.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.databinding.FragmentRegisterBinding
import br.com.casalmoney.app.unauthenticated.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = FragmentRegisterBinding.inflate(inflater, container, false)

            binding.viewModel = viewModel
            binding.fragment = this
            binding.lifecycleOwner = this

            requireActivity().onBackPressedDispatcher.addCallback {
                back(binding.root)
            }
            return binding.root
    }

    fun back (view: View) {
        findNavController().popBackStack()
    }

    fun register(view: View) {

    }
}