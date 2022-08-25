package com.example.tbc_course_22.ui.main.authentication.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbc_course_22.R
import com.example.tbc_course_22.databinding.FragmentHomeBinding
import com.example.tbc_course_22.ui.main.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val args: HomeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView2.text = args.email


        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.getPreferences().collect{
                if(it.contains(stringPreferencesKey("tokentest"))){
                    binding.textView2.text = it[stringPreferencesKey("tokentest")]

                }
            }
        }

        binding.logOut.setOnClickListener {


            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.clear()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMainFragment())
            }

        }



    }

}