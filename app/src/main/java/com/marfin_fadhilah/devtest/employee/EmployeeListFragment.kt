package com.marfin_fadhilah.devtest.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.marfin_fadhilah.devtest.R
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.ui.EmployeeAdapter
import com.marfin_fadhilah.devtest.databinding.EmployeeListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class EmployeeListFragment: Fragment() {
    companion object {
        fun launch(fragmentManager: FragmentManager) {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, EmployeeListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private var _binding: EmployeeListFragmentBinding? = null
    private val binding get() = _binding!!

    private val employeeAdapter = EmployeeAdapter()

    private val employeeListViewModel: EmployeeListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().supportFragmentManager.beginTransaction()
        _binding = EmployeeListFragmentBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar!!.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initObserve()
            initComponent()
        }
    }

    private fun initComponent() {
        with(binding.rvEmployee) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

            employeeAdapter.onEditClick = { employee ->
                employeeListViewModel.updateEmployee(employee)
            }

            employeeAdapter.onDeleteClick = { employee ->
                employeeListViewModel.deleteEmployee(employee)
            }

            adapter = employeeAdapter
        }
    }

    private fun initObserve() {
        employeeListViewModel.employee.observe(viewLifecycleOwner) { employee ->
            if (employee != null) {
                when (employee) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        employeeAdapter.setData(employee.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            employee.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }

        employeeListViewModel.editResult.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.success)
                            .setMessage(R.string.update_success)
                            .setPositiveButton(R.string.ok) { _, _ ->
                                employeeListViewModel.refreshEmployee()
                            }
                            .create()
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.something_wrong)
                            .setMessage(response.message)
                            .setPositiveButton(R.string.ok) { _, _ ->
                                employeeListViewModel.refreshEmployee()
                            }
                            .create()
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

        employeeListViewModel.editResult.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.success)
                            .setMessage(R.string.delete_success)
                            .setPositiveButton(R.string.ok) { _, _ ->
                                employeeListViewModel.refreshEmployee()
                            }
                            .create()
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.something_wrong)
                            .setMessage(response.message)
                            .setPositiveButton(R.string.ok) { _, _ ->
                                employeeListViewModel.refreshEmployee()
                            }
                            .create()
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}