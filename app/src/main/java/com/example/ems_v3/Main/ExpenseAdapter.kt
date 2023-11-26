package com.example.ems_v3.Main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ems_v3.R

class ExpenseAdapter(private val expenseList: List<Pair<String, Double>>) :
    RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewExpenseType: TextView = itemView.findViewById(R.id.textViewExpenseType)
        val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = expenseList[position]

        // Assuming that currentItem.expenseList is a list of Pair<String, Double>
        // You may need to adjust this based on your ExpenseItem structure
        if (currentItem.expenseList.isNotEmpty()) {
            val (expenseType, expenseAmount) = currentItem.expenseList[0]
            holder.textViewExpenseType.text = expenseType
            holder.textViewAmount.text = expenseAmount.toString()
        }
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }
}


