package com.sps.phoneupdatemyanmar.ui.dialog_fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.sps.phoneupdatemyanmar.R

class ReviewDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogView = activity?.layoutInflater?.inflate(R.layout.fragment_review_dialog, null)
        val builder = AlertDialog.Builder(activity)         //create dialog object

        //builder.setTitle("Review")
        // builder.setIcon(R.drawable.info)
        builder.setView(dialogView)

        val review = arguments?.getString("Review", "unknown")
        val reviewView: TextView = dialogView!!.findViewById(R.id.review_text)
        reviewView.text = review

        val buttonOk: Button = dialogView.findViewById(R.id.review_button)
        buttonOk.setOnClickListener {
            dialog?.dismiss()
        }
        return builder.create()
    }
}