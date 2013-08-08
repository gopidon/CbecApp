package in.gov.cbec;




import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import in.gov.cbec.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import in.gov.cbec.util.CbecConstants;
import in.gov.cbec.util.RefreshActsAndManuals;

public class CbecWebViewActivity extends SherlockActivity {
	WebView wv;
	private LinearLayout container;  
	private Button nextButton, closeButton;  
	private EditText findBox;
	private static final int SEARCH_MENU_ID = Menu.FIRST;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cbecwebview);
		
		if (savedInstanceState != null) {
            return;
		}
		
		String link = this.getIntent().getStringExtra(CbecConstants.CBEC_WEB_SHOW_LINK);
		
		wv = (WebView)this.findViewById(R.id.wv1);
        
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new CbecWebViewClient(this));
        //WebView wv = (WebView)this.getView();  
        //Log.d(CbecConstants.CBEC_DEBUG_MSG_TAG+":CbecWebViewActivity:shouldOverrideUrlLoading()", link);
        wv.loadUrl(link);
        //wv.loadDataWithBaseURL("http://www.servicetax.gov.in", "st-rules-home.htm", "text/html", "utf-8", null);
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.webviewmenu, menu);
	    return true;
	} 
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_search:
	      doSearch();
	      break;
	    default:
	      break;
	    }
	    return true;
	  }
	
	
	 private void doSearch()
	 {
		 	container = (LinearLayout)findViewById(R.id.layoutId);  
		 	container.removeAllViews();
		 	container.setBackgroundColor(Color.WHITE);
		    nextButton = new Button(this);  
		    nextButton.setText("Next");  
		    nextButton.setOnClickListener(new OnClickListener(){  
		    	public void onClick(View v){  
		    	wv.findNext(true);  
		    	}  
		    	});  
		    container.addView(nextButton);  
		      
		    closeButton = new Button(this);  
		    closeButton.setText("Close");  
		    closeButton.setOnClickListener(new OnClickListener(){  
		    	@Override  
		    	public void onClick(View v){  
		    	container.removeAllViews();  
		    	  
		    	}  
		    	});  
		    container.addView(closeButton);  
		      
		    findBox = new EditText(this);  
		    findBox.setMinEms(30);  
		    findBox.setSingleLine(true);  
		    findBox.setHint("Search");
		    
		    findBox.setOnKeyListener(new OnKeyListener(){  
		    	public boolean onKey(View v, int keyCode, KeyEvent event){  
		    	if((event.getAction() == KeyEvent.ACTION_DOWN) && ((keyCode == KeyEvent.KEYCODE_ENTER))){  
		    	wv.findAll(findBox.getText().toString());  
		    	  
		    	try{  
		    	Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);  
		    	m.invoke(wv, true);  
		    	}catch(Exception ignored){}  
		    	}  
		    	return false;  
		    	}  
		    	});  
		    container.addView(findBox);  
	 }
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack())
	    {
	        wv.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
}
