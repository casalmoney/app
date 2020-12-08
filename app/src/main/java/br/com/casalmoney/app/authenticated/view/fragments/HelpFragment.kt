package br.com.casalmoney.app.authenticated.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.viewModel.HelpViewModel
import br.com.casalmoney.app.databinding.FragmentHelpBinding
import br.com.casalmoney.app.authenticated.view.adapters.ChatAdapter
import br.com.casalmoney.app.authenticated.view.adapters.NewsAdapter
import br.com.casalmoney.app.authenticated.viewModel.HomeViewModel
import br.com.casalmoney.app.utils.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class HelpFragment: Fragment() {
    private lateinit var binding: FragmentHelpBinding
    private lateinit var recyclerView: RecyclerView
    private val progressDialog = CustomProgressDialog()

    private val viewModel: HelpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    fun presentChat(view: View) {
        findNavController().navigate(R.id.action_helpFragment_to_chatFragment)
    }

    fun setupRecyclerView () {
        recyclerView = binding.rvNews
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        viewModel.news.observe(viewLifecycleOwner, { list ->
            recyclerView.adapter = NewsAdapter(list) {
                Log.d("CLICOU", "RESULTADO DO CLICK")
            }
        })

        viewModel.getNews()
    }
}