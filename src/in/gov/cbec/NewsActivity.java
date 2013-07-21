package in.gov.cbec;

import in.gov.cbec.R;
import in.gov.cbec.dialogs.SimpleErrorAlertDialog;
import in.gov.cbec.util.CbecUtils;
import in.gov.cbec.util.RssFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

public class NewsActivity extends FragmentActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
 
        if (savedInstanceState == null) {
          if(CbecUtils.isOnline(this))
          {
        	  addRssFragment();
          }
          else
          {
        	  showDialog();
          }
        }
        
    }
 
    private void addRssFragment() {
        
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RssFragment fragment = new RssFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    
    void showDialog() {
	    DialogFragment newFragment = SimpleErrorAlertDialog.newInstance(
	            "Error");
	    newFragment.show(getSupportFragmentManager(), "dialog");
	}
 
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }

}

