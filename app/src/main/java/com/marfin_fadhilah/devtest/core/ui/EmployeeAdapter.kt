package com.marfin_fadhilah.devtest.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marfin_fadhilah.devtest.R
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.databinding.ItemListEmployeeBinding
import java.util.ArrayList

@SuppressLint("SetTextI18n")
class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.ListViewHolder>() {

    private var listData = ArrayList<Employee>()
    var onEditClick: ((Employee) -> Unit)? = null
    var onDeleteClick: ((Employee) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Employee>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_employee, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListEmployeeBinding.bind(itemView)
        private val context = itemView.context

        private val id = context.getString(R.string.employee_id)
        private val name = context.getString(R.string.employee_name)
        private val salary = context.getString(R.string.employee_salary)
        private val age = context.getString(R.string.employee_age)

        fun bind(data: Employee) {
            with(binding) {
                tvItemId.text = "$id: ${data.id}"
                tvItemName.text = "$name: ${data.name}"
                tvItemSalary.text = "$salary: ${data.salary}"
                tvItemAge.text = "$age: ${data.age}"
            }
        }

        init {
            binding.btnEdit.setOnClickListener {
                onEditClick?.invoke(listData[adapterPosition])
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClick?.invoke(listData[adapterPosition])
            }
        }
    }
}