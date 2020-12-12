package br.com.casalmoney.app.authenticated.view.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.view.activities.MainActivity
import br.com.casalmoney.app.authenticated.view.adapters.TransactionAdapter
import br.com.casalmoney.app.authenticated.viewModel.HomeViewModel
import br.com.casalmoney.app.databinding.FragmentHomeBinding
import br.com.casalmoney.app.utils.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
@WithFragmentBindings
class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: AppCompatImageView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private val progressDialog = CustomProgressDialog()
    private var presentingProgressDialog: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getUserDetails()
        setupLoading()
        setupBindings()
        setupSwipeRefresh()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTransactions()
    }

    private fun setupBindings() {
        recyclerView = binding.recyclerViewTransactions
        emptyState = binding.emptyState
        swipeRefresh = binding.swipeRefresh
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.getTransactions()
        }
    }

    private fun setupLoading() {
        viewModel.disposable = viewModel.isLoading.subscribe { isLoading ->
            if (isLoading && !presentingProgressDialog) {
                activity?.let {
                    progressDialog.show(it)
                    presentingProgressDialog = true
                }
            } else {
                progressDialog.dialog.dismiss()
                presentingProgressDialog = false
            }
        }
    }

    private fun setupRecyclerView() {

        val layoutManager = StaggeredGridLayoutManager(
            1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        viewModel.transactionList.observe(viewLifecycleOwner, { list ->

            if (list.isEmpty()) {
                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyState.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                if(recyclerView.adapter == null) {
                    recyclerView.adapter = TransactionAdapter(list) {
                        viewModel.isLoading.onNext(false)
                        progressDialog.dialog.dismiss()
                        presentingProgressDialog = false
                        (activity as? MainActivity)?.selectedTransaction = it
                        findNavController().navigate(R.id.action_homeFragment_to_transactionDetailFragment)
                    }
                } else {
                    (recyclerView.adapter as? TransactionAdapter)?.updateDataSet(list)
                }
            }

            swipeRefresh.isRefreshing = false
        })

        viewModel.getTransactions()
    }

    fun logout(view: View) {
        val dialogBuilder = AlertDialog.Builder(activity)

        dialogBuilder.setMessage(getString(R.string.logout_title))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.logout()
                findNavController().navigate(R.id.action_homeFragment_to_unAuthenticatedActivity)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.logout))
        alert.show()
    }

    fun openModalTransactionFragment(view: View) {
        ModalTransactionFragment().show(requireActivity().supportFragmentManager, ModalTransactionFragment.TAG)
    }

    fun openModalTransactionVariableFragment(view: View) {
        ModalTransactionVariableFragment().show(requireActivity().supportFragmentManager, ModalTransactionVariableFragment.TAG)
    }
}