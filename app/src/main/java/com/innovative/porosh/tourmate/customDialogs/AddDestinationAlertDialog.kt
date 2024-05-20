package com.innovative.porosh.tourmate.customDialogs

import android.app.Dialog

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.innovative.porosh.tourmate.R


class AddDestinationAlertDialog(val callback: (String) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = requireActivity().layoutInflater.inflate(R.layout.add_destination_alert_layout, null)
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Destination Name")
            .setView(layout)
            .setPositiveButton("Add") { dialog, which ->
                val titleET: EditText = layout.findViewById(R.id.addDestinationAlertET)
                callback(titleET.text.toString())
            }
            .setNegativeButton("Cancel", null)
        return builder.create()
    }
}