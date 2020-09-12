package br.com.casalmoney.app.unauthenticated.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.databinding.FragmentRecoverPasswordBinding
import br.com.casalmoney.app.unauthenticated.interactor.RecoverPassworrdInterector
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


    override fun onResume() {
        super.onResume()
        observer()
    }

    fun recoverPassword(view: View) {
        viewModel.recoverPassword()
    }

    fun observer() {
        viewModel.responseRecover.observe(viewLifecycleOwner, Observer {
            if(it?.message.isNullOrEmpty() || it?.message.isNullOrBlank()) {
                Toast.makeText(context, R.string.recover_password_message, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, it?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }




}