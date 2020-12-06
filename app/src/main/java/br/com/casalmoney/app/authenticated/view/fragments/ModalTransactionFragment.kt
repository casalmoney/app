package br.com.casalmoney.app.authenticated.view.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.viewModel.HomeViewModel
import br.com.casalmoney.app.databinding.FragmentModalTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class ModalTransactionFragment : DialogFragment() {

    companion object {
        val TAG = "ModalTransactionFragment"
    }

    private lateinit var binding: FragmentModalTransactionBinding
    private val viewModel: HomeViewModel by viewModels()

    private var fixedExpenses = listOf("Internet", "Moradia", "Alimentação", "Lazer", "Educação", "Agua", "Luz", "Telefone", "Transporte", "Outros")
    private var typeExpenses: String = ""
    private var auxValueExpenses: String = ""
    var valueExpenses: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalTransactionBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinnerAdapter()
    }


    override fun onResume() {
        super.onResume()
        binding.etAmountValue.addTextChangedListener( object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString() != auxValueExpenses){
                    binding.etAmountValue.removeTextChangedListener(this);

                    val formatted = viewModel.formatToCurrency(p0)
                    auxValueExpenses = formatted;

                    binding.etAmountValue.setText(formatted);
                    binding.etAmountValue.setSelection(formatted.length);
                    binding.etAmountValue.addTextChangedListener(this);
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not use
            }
            override fun afterTextChanged(p0: Editable?) {
                //not use
            }
        })
    }

    private fun setupSpinnerAdapter() {
        val spinner = binding.spinnerTypeTransaction
        val sortFixedExpenses = fixedExpenses.sortedBy { it.toString() }

        val adapter: ArrayAdapter<String> = object: ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            sortFixedExpenses
        ){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition){
                    view.background = ColorDrawable(Color.parseColor("#f5f4f2"))
                    view.setTextColor(Color.parseColor("#10ad10"))
                }

                return view
            }
        }

        // finally, data bind spinner with adapter
        spinner.adapter = adapter


        // spinner on item selected listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                typeExpenses = fixedExpenses.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }
    }

    fun registerExpenses(view: View) {
        if(valueExpenses.isEmpty() || valueExpenses.isBlank()) {
            binding.textInputAmount.error = getString(R.string.alert_empty_field)
        } else {
            binding.textInputAmount.isErrorEnabled = false
            viewModel.saveTransaction(valueExpenses, typeExpenses)
            dialog?.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

}