package com.example.tbc_course_22.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_course_22.R
import com.example.tbc_course_22.databinding.FragmentMainBinding
import com.example.tbc_course_22.extensions.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.registerBtn.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToRegisterFragment())
        }

        binding.loginBtn.setOnClickListener {
            login()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.loginState.collect {
                    when (it) {
                        is Resource.Error -> {
                            Snackbar.make(view, it.errorData, Snackbar.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            Log.d("loading", "${it.loader}")
                        }
                        is Resource.Success -> {
                            Snackbar.make(
                                view,
                                getString(R.string.welcome) + binding.emailEditText.text.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(MainFragmentDirections.actionMainFragmentToHomeFragment())
                        }
                    }
                }
            }
        }


    }

    private fun login() {
        if (binding.emailEditText.text!!.isNotEmpty() && binding.passwordEditText.text!!.isNotEmpty()) {
            viewModel.logIn(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        } else {
            Snackbar.make(requireView(), getString(R.string.fill_all), Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}