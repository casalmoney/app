package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.casalmoney.app.authenticated.view.activities.MainActivity
import br.com.casalmoney.app.authenticated.viewModel.TransactionDetailViewModel
import br.com.casalmoney.app.databinding.FragmentTransactionDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class TransactionDetailFragment : Fragment() {
    private lateinit var binding: FragmentTransactionDetailBinding

    private val viewModel: TransactionDetailViewModel by lazy {
        ViewModelProvider(this).get(TransactionDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)

        viewModel.transaction = (activity as? MainActivity)?.selectedTransaction

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    fun changeLocation(view: View) {
        viewModel.changeLocation()
    }
}