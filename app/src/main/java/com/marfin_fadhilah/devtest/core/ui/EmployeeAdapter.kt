package com.marfin_fadhilah.devtest.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marfin_fadhilah.devtest.R
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.databinding.ItemListEmployeeBinding
import kotlinx.android.synthetic.main.employee_info_edittext.view.*
import java.util.ArrayList


@SuppressLint("SetTextI18n")
class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.ListViewHolder>() {

    private var listData = ArrayList<Employee>()
    var onUpdateClick: ((Employee) -> Unit)? = null
    var onDeleteClick: ((Employee) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Employee>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_employee, parent, false)
        )

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
                infoItemId.setLabelAndValue("$id:", "${data.id}")
                infoItemName.setLabelAndValue("$name:", data.name)
                infoItemSalary.setLabelAndValue("$salary:", "${data.salary}")
                infoItemAge.setLabelAndValue("$age:", "${data.age}")
            }
        }

        init {
            binding.btnEdit.setOnClickListener {
                switchToEditMode()
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClick?.invoke(listData[adapterPosition])
            }
        }

        private fun switchToEditMode() {
            with(binding) {
                btnEdit.text = context.getString(R.string.update)
                btnDelete.text = context.getString(R.string.cancel)

                setEnableEditText(true)

                btnEdit.setOnClickListener {
                    if (onUpdateClick != null) {
                        onUpdateClick?.invoke(
                            Employee(
                                infoItemId.value.toInt(),
                                infoItemName.value,
                                infoItemSalary.value.toInt(),
                                infoItemAge.value.toInt()
                            )
                        )
                        switchToViewOnly()
                    }
                }
                btnDelete.setOnClickListener {
                    switchToViewOnly()
                }
            }
        }

        private fun switchToViewOnly() {
            with(binding) {
                btnEdit.text = context.getString(R.string.edit)
                btnDelete.text = context.getString(R.string.delete)

                setEnableEditText(false)

                btnEdit.setOnClickListener {
                    switchToEditMode()
                }
                btnDelete.setOnClickListener {
                    onDeleteClick?.invoke(listData[adapterPosition])
                }
            }
        }

        private fun setEnableEditText(isEnable: Boolean) {
            with(binding) {
                infoItemId.setEnable(isEnable)
                infoItemName.setEnable(isEnable)
                infoItemAge.setEnable(isEnable)
                infoItemSalary.setEnable(isEnable)
            }
        }
    }
}