package com.example.ems_v3.Main;



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ems_v3.R

import java.util.List;

class HomeAdapter(private val dataList:List<ExpenseItem>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val recyclerViewExpenses: RecyclerView = itemView.findViewById(R.id.recyclerViewExpenses)
        val textViewCustomerName:TextView = itemView.findViewById(R.id.textViewCustomerName)
        var textViewMissionTitle: TextView = itemView.findViewById(R.id.textViewMissionTitle)
        val textViewExpenseDate: TextView = itemView.findViewById(R.id.textViewExpenseDate)
//        val printButton: Button = itemView.findViewById(R.id.PrintButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_item_layout, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to views here
        val currentItem = dataList[position]


        // Bind data to views here
      /*  holder.textViewExpenseDate.text = "11-04-2023"*//*expenseItem.expenseDate*//*
        holder.textViewMissionTitle.text = "installation "*//*expenseItem.missionTitle*//*
        holder.textViewCustomerName.text = "CHO "*//*expenseItem.customerName*//*
*/
        holder.textViewMissionTitle.text = currentItem.missionTitle ;
        holder.textViewCustomerName.text = currentItem.customerName ;
        holder.textViewExpenseDate.text = currentItem.expenseDate ;




        // Set up the RecyclerView for the list of expenses

        val expenseAdapter = ExpenseAdapter(currentItem.expenseList)
        holder.recyclerViewExpenses.adapter = expenseAdapter
        holder.recyclerViewExpenses.layoutManager =
            LinearLayoutManager(holder.recyclerViewExpenses.context)

     /*
        sebutton function , modification and validation
        holder.printButton.setOnClickListener {
            exportToPDF()
        }*/


        // Check validation status and update background accordingly
        if (currentItem.customerName.equals("Customer A")) {
            holder.itemView.setBackgroundResource(R.color.validated_expense)
        } else {
            holder.itemView.setBackgroundResource(R.color.non_validated_expense)
        }



    }

    override fun getItemCount(): Int {
        return dataList.size
    }


// set buton function edit and validate
    private fun exportToPDF() {



    }
}


