package com.example.tbc_course_22.ui.main.authentication.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbc_course_22.R
import com.example.tbc_course_22.databinding.FragmentHomeBinding
import com.example.tbc_course_22.databinding.FragmentRegisterBinding
import com.example.tbc_course_22.extensions.DataStore
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestString"){key,bundle ->
            val result = bundle.getString("bundleKeyString")
            binding.textView2.text = result
        }
    }

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

        binding.logOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch{
                DataStore(requireActivity().application).also {
                    it.saveState("KEYTEST", "")
                    it.saveState("KEYTEST1", "")

                }
            }
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMainFragment())
        }


    }

}