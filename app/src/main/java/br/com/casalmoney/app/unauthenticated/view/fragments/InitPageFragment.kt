package br.com.casalmoney.app.unauthenticated.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.databinding.FragmentInitPageBinding
import br.com.casalmoney.app.unauthenticated.viewmodel.InitPageViewModel
import com.google.firebase.auth.FirebaseAuth

class InitPageFragment : Fragment() {
    private lateinit var binding: FragmentInitPageBinding

    private val viewModel: InitPageViewModel by lazy {
        ViewModelProvider(this).get(InitPageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitPageBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    fun goToRegister (view : View) {
        findNavController().navigate(R.id.action_initPageFragment_to_registerFragment)
    }

    fun goToLogin (view : View) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(R.id.action_initPageFragment_to_mainActivity)
        } else {
            findNavController().navigate(R.id.action_initPageFragment_to_loginFragment)
        }
    }
}