package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.viewModel.HomeViewModel
import br.com.casalmoney.app.databinding.FragmentModalTransactionVariableBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class ModalTransactionVariableFragment : DialogFragment() {
    companion object {
        val TAG = "ModalTransactionVariableFragment"
    }

    private lateinit var binding: FragmentModalTransactionVariableBinding
    private val viewModel: HomeViewModel by viewModels()

    var valueExpenses: String = ""
    var descExpense: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalTransactionVariableBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }


    fun registerExpenses(view: View) {
        val showErro = (descExpense.isBlank() || descExpense.isEmpty()) && (valueExpenses.isEmpty() || valueExpenses.isBlank())
        if(showErro) {
            binding.textInputAmount.error = getString(R.string.alert_empty_field)
        } else {
            binding.textInputAmount.isErrorEnabled = false
            viewModel.saveTransaction(valueExpenses, descExpense)
            dialog?.dismiss()
        }
    }
}
