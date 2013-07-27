package in.gov.cbec;


import in.gov.cbec.util.CbecConstants;
import in.gov.cbec.util.CbecMessages;



import android.app.ProgressDialog;
import android.graphics.Bitmap;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CbecWebViewClient extends WebViewClient {
	
    ProgressDialog pDialog;
    CbecWebViewActivity callingActivity;
    
    public CbecWebViewClient(CbecWebViewActivity act)
    {
    	pDialog = new ProgressDialog(act);
    	pDialog.setMessage(CbecMessages.CBEC_MSG_LOADING);
    	callingActivity = act;
    }
	
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
	
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        pDialog.show();
	}
	
	public void onPageFinished(WebView view, String url) {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
	
	

	
	
}
