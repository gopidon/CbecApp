package in.gov.cbec.dialogs;

import in.gov.cbec.util.CbecMessages;
import in.gov.cbec.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SimpleErrorAlertDialog extends DialogFragment {
	public static SimpleErrorAlertDialog newInstance( int iconType, String title, String message) {
		SimpleErrorAlertDialog frag = new SimpleErrorAlertDialog();
        Bundle args = new Bundle();
        args.putInt("iconType", iconType);
        args.putString("title", title);
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int iconType = getArguments().getInt("iconType");
        String title = getArguments().getString("title");
        String msg = getArguments().getString("message");

        AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity());
	    errorDialog.setTitle(title);
	    errorDialog.setMessage(msg);
	    errorDialog.setIcon(iconType);
	    errorDialog.setNegativeButton("OK",(DialogInterface.OnClickListener)getActivity());
	    AlertDialog errorAlert = errorDialog.create();
	    return errorAlert;
    }
    
    
}
