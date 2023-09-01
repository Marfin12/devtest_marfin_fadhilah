package com.marfin_fadhilah.devtest.home

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marfin_fadhilah.devtest.R
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.utils.Utils.validateInput
import com.marfin_fadhilah.devtest.databinding.HomeFragmentBinding
import com.marfin_fadhilah.devtest.employee.EmployeeListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment: Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar!!.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initComponent()
            initObserve()
        }
    }

    private fun initComponent() {
        with(binding) {
            edtName.validateInput(
                requireContext().resources.getString(R.string.empty_input_error)
            ) { text -> text.isNotEmpty() }

            edtSalary.validateInput(
                requireContext().resources.getString(R.string.numeric_input_error)
            ) { text -> TextUtils.isDigitsOnly(text) }

            edtAge.validateInput(
                requireContext().resources.getString(R.string.numeric_input_error)
            ) { text -> TextUtils.isDigitsOnly(text) }

            btnEmployeeList.setOnClickListener {
                EmployeeListFragment.launch(requireActivity().supportFragmentManager)
            }

            btnSubmit.setOnClickListener {
                if (isNotEmptyInput() && isValidateAllInput()) {
                    homeViewModel.postEmployee(
                        Employee(
                            id = 100,
                            name = edtName.text.toString(),
                            salary = edtSalary.text.toString().toInt(),
                            age = edtAge.text.toString().toInt()
                        )
                    )
                }
                else {
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.error)
                        .setMessage(R.string.input_correctly)
                        .setPositiveButton(R.string.ok) { _, _ -> }
                        .create()
                        .show()
                }
            }
        }
    }

    private fun initObserve() {
        homeViewModel.postResult.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.success)
                            .setMessage(R.string.delete_success)
                            .setPositiveButton(R.string.ok) { _, _ -> }
                            .create()
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.something_wrong)
                            .setMessage(response.message)
                            .setPositiveButton(R.string.ok) { _, _ -> }
                            .create()
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun isValidateAllInput(): Boolean =
        (binding.edtName.error == null
                && binding.edtSalary.error == null
                && binding.edtAge.error == null)

    private fun isNotEmptyInput(): Boolean =
        (binding.edtName.text.isNotEmpty()
                && binding.edtSalary.text.isNotEmpty()
                && binding.edtAge.text.isNotEmpty())
}