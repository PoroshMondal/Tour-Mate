package com.innovative.porosh.tourmate.customDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.model.ExpenseModel

class AddExpenseDialog(val callback: (ExpenseModel) -> Unit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = requireActivity().layoutInflater.inflate(R.layout.add_expense_layout,null)
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.add_expense_title))
            .setView(layout)
            .setPositiveButton(getString(R.string.save)){ dialog, which ->
                val titleEt: EditText = layout.findViewById(R.id.addExpenseTitleET)
                val amountEt: EditText = layout.findViewById(R.id.addExpenseAmountET)

                // need to validate the fields
                val expenseModel = ExpenseModel(
                    title = titleEt.text.toString(),
                    amount = amountEt.text.toString().toInt()
                )
                callback(expenseModel)
            }
            .setNegativeButton(getString(R.string.cancel),null)

        return builder.create()
    }
}