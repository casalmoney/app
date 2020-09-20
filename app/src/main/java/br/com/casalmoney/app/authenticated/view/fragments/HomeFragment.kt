package br.com.casalmoney.app.authenticated.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.viewModel.HomeViewModel
import br.com.casalmoney.app.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

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
}