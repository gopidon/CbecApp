package in.gov.cbec;

import in.gov.cbec.util.CbecConstants;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CbecWebViewClient extends WebViewClient {
	
    
	
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		Log.d(CbecConstants.CBEC_DEBUG_MSG_TAG+":CbecWebViewClient:shouldOverrideUrlLoading()", url);
		if(url.contains(".pdf")){
			url = CbecConstants.CBEC_PREFIX_GDOCS_LINK + url;
			view.loadUrl(url);
			return true;
			
		}
		else
		{
			view.loadUrl(url);
			return true;
		}
		
    }
	
	
}
