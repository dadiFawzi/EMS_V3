package com.example.ems_v3.Report;



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ems_v3.Main.ExpenseItem
import com.example.ems_v3.R

import java.util.List;

class ReportAdapter(private val dataList: List<com.example.ems_v3.Report.ExpenseItem>) :
    RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val recyclerViewExpenses: RecyclerView = itemView.findViewById(R.id.recyclerViewExpenses)
        val textViewCustomerName:TextView = itemView.findViewById(R.id.textViewCustomerName)
        val textViewMissionTitle: TextView = itemView.findViewById(R.id.textViewMissionTitle)
        val textViewExpenseDate: TextView = itemView.findViewById(R.id.textViewExpenseDate)
//        val printButton: Button = itemView.findViewById(R.id.PrintButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to views here
        //val expenseItem = dataList[position]

        val expenses1 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )

        val expenses2 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )
        val expenses3 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )
        val expenses4 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )

// Initialize the list of ExpenseItem
        val expenseItem: kotlin.collections.List<ExpenseItem> = listOf(
            ExpenseItem("2023-07-24", "Business Trip 1", 125.30,"Customer A", expenses1),
            ExpenseItem("2023-07-25", "Business Trip 2", 85.40,"Customer B", expenses2),
            ExpenseItem("2023-07-24", "Business Trip 3", 125.30,"Customer A", expenses3),
            ExpenseItem("2023-07-25", "Business Trip 4", 85.40,"Customer B", expenses4)
        )


                    // Bind data to views here
        holder.textViewExpenseDate.text = "11-04-2023"/*expenseItem.expenseDate*/
        holder.textViewMissionTitle.text = "installation "/*expenseItem.missionTitle*/
        holder.textViewCustomerName.text = "CHO "/*expenseItem.customerName*/

        // Set up the RecyclerView for the list of expenses

        val expenseAdapter = ExpenseAdapter(expenseItem)
        holder.recyclerViewExpenses.adapter = expenseAdapter
        holder.recyclerViewExpenses.layoutManager =
            LinearLayoutManager(holder.recyclerViewExpenses.context)

     /*   holder.printButton.setOnClickListener {
            exportToPDF()
        }*/
    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    private fun exportToPDF() {



    }
}


