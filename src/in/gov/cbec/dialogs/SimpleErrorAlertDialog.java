package in.gov.cbec.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SimpleErrorAlertDialog extends DialogFragment {
	public static SimpleErrorAlertDialog newInstance(String title) {
		SimpleErrorAlertDialog frag = new SimpleErrorAlertDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity());
	    errorDialog.setTitle(title);
	    errorDialog.setMessage("No internet connection.");
	    errorDialog.setNegativeButton("OK",(DialogInterface.OnClickListener)getActivity());
	    AlertDialog errorAlert = errorDialog.create();
	    return errorAlert;
    }
}
