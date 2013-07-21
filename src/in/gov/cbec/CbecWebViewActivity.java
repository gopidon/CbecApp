package in.gov.cbec;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.gov.cbec.util.CbecConstants;

public class CbecWebViewActivity extends Activity {
	WebView wv;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cbecwebview);
		
		if (savedInstanceState != null) {
            return;
		}
		
		String link = this.getIntent().getStringExtra(CbecConstants.CBEC_WEB_SHOW_LINK);
		
		wv = (WebView)this.findViewById(R.id.wv1);
        
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new CbecWebViewClient());
        //WebView wv = (WebView)this.getView();  
        Log.d(CbecConstants.CBEC_DEBUG_MSG_TAG+":CbecWebViewActivity:shouldOverrideUrlLoading()", link);
        wv.loadUrl(link);
		
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
