package br.com.casalmoney.app.authenticated.view.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.viewModel.HomeViewModel
import br.com.casalmoney.app.databinding.FragmentModalTransactionBinding

class ModalTransactionFragment : DialogFragment() {

    private lateinit var binding: FragmentModalTransactionBinding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    companion object {
        const val TAG = "ModalTransactionFragment"
    }

    private var fixedExpenses = listOf("Internet", "Moradia", "Alimentação", "Lazer", "Educação", "Agua", "Luz", "Telefone", "Outros")
    private var typeExpenses: String = ""
    var valueExpenses: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalTransactionBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        val spinner = binding.spinnerTypeTransaction

        val adapter: ArrayAdapter<String> = object: ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            fixedExpenses
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

//        val format = NumberFormat.getCurrencyInstance()
//        format.setCurrency(Currency.getInstance("BRL"))
//        binding.textInputAmount.editText?.addTextChangedListener { it ->
//            valueExpenses = format.format(it.toString().toDouble())
//            viewModel.valueExpenses.value = format.format(it.toString().toDouble())
//        }
    }


    fun registerExpenses(view: View) {
        if(valueExpenses.isEmpty() || valueExpenses.isBlank()) {
            binding.textInputAmount.error = getString(R.string.alert_empty_field)
        } else {
            binding.textInputAmount.isErrorEnabled = false
            viewModel.saveTransaction(valueExpenses, typeExpenses)
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