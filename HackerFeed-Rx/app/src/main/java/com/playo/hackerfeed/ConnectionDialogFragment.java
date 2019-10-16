package com.playo.hackerfeed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * Created by vichu on 11/10/17.
 */

public class ConnectionDialogFragment extends DialogFragment{

    private DialogInterface.OnClickListener positiveClickListener;
    private DialogInterface.OnClickListener negativeClickListener;

    private DialogInterface.OnClickListener baseClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String message = null;
        String positiveButtonText = null;
        String negativeButtonText = null;
        if (args != null) {
            message = args.getString("message", null);
            positiveButtonText = args.getString("positiveButtonText", null);
            negativeButtonText = args.getString("negativeButtonText", null);
        }

        message = "You are currently not connected to the internet. Please change your Settings to enable connectivity or Ignore to continue.";
        positiveButtonText = "Ignore";
        negativeButtonText = "Settings";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (positiveClickListener == null)
            positiveClickListener = baseClickListener;
        if (negativeClickListener == null)
            negativeClickListener = baseClickListener;

        builder.setMessage(message)
                .setTitle("Alert")
                // Add action buttons
                .setPositiveButton(positiveButtonText, positiveClickListener)
                .setNegativeButton(negativeButtonText, negativeClickListener);


        AlertDialog dialog = builder.create();
        setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    public void setPositiveClickListener(DialogInterface.OnClickListener positiveClickListener) {
        this.positiveClickListener = positiveClickListener;
    }

    public void setNegativeClickListener(DialogInterface.OnClickListener negativeClickListener) {
        this.negativeClickListener = negativeClickListener;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelCallback != null) {
            cancelCallback.onCancelCalled();
        }
    }

    public void setOnCancelCallback(CancelCallback cancelCallback) {
        this.cancelCallback = cancelCallback;
    }

    public interface CancelCallback {
        public void onCancelCalled();
    }

    private CancelCallback cancelCallback;

}
