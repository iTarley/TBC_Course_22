package com.example.tbc_course_22.ui.main.authentication.registration

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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.tbc_course_22.R
import com.example.tbc_course_22.databinding.FragmentRegisterBinding
import com.example.tbc_course_22.extensions.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.registerBtn.setOnClickListener {
            registration()
        }



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.registerState.collect{
                    when(it){
                        is Resource.Error -> {
                            Snackbar.make(view, it.errorData, Snackbar.LENGTH_SHORT).show()
                            Log.d("error", "${it.errorData}")
                        }
                        is Resource.Loading -> Log.d("loading", "${it.loader}")
                        is Resource.Success -> {
                            Snackbar.make(
                                view,
                                "Registered:" + binding.emailEditText.text.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToMainFragment())
                        }
                    }
                }
            }
        }


    }

    private fun registration() {
        if (binding.emailEditText.text!!.isNotEmpty() && binding.passwordEditText.text!!.isNotEmpty() && isPasswordMatch(binding.passwordEditText.text.toString(),binding.repeatPasswordEditText.text.toString())) {
            viewModel.register(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        } else {
            Snackbar.make(requireView(), getString(R.string.fill_all), Snackbar.LENGTH_SHORT).show()
        }
    }
    private fun isPasswordMatch(password: String,repeatPassword:String):Boolean =
        password == repeatPassword


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}