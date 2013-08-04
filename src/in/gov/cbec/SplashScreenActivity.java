package in.gov.cbec;

import in.gov.cbec.dialogs.SimpleErrorAlertDialog;
import in.gov.cbec.util.CbecMessages;
import in.gov.cbec.util.CbecUtils;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class SplashScreenActivity extends FragmentActivity implements DialogInterface.OnClickListener {
	private static int SPLASH_TIME_OUT = 1000;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        if(!CbecUtils.isOnline(this))
        {
        	showDialog();
        }
        else
        {
        	new Handler().postDelayed(new Runnable() {
        		 
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
     
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreenActivity.this, CbecMainActivity.class);
                    startActivity(i);
     
                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        }
 
        
    
    void showDialog() {
	    DialogFragment newFragment = SimpleErrorAlertDialog.newInstance(R.drawable.alert,CbecMessages.CBEC_MSG_ERR,CbecMessages.CBEC_MSG_NO_INTERNET_ERR);
	    newFragment.show(getSupportFragmentManager(), "dialog");
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		gotoMainActivity();
	}
	
	public void gotoMainActivity()
	{
		Intent i = new Intent(SplashScreenActivity.this, CbecMainActivity.class);
        startActivity(i);
	}
}
