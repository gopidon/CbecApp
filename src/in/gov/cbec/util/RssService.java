package in.gov.cbec.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;


import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class RssService extends IntentService {
	 
	private static final String RSS_FEED = "";
    private static final String RSS_FEED1 = "http://feeds.feedburner.com/indiantaxupdates";
    private static final String RSS_FEED2 = "http://feeds.feedburner.com/SimpleTaxIndia";
    private static final String RSS_FEED3 = "http://feeds2.feedburner.com/CaclubindiacomArticles";
    
    private static final String[] RSS_FEEDS = {RSS_FEED1,RSS_FEED2,RSS_FEED3};
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
 
    public RssService() {
        super("RssService");
    }
 
    @Override
    protected void onHandleIntent(Intent intent) {
        
    	//Log.v("CBEC","Service Started");
        List<RssItem> rssItems = null;
        List<RssItem> rssAllItems = new ArrayList<RssItem>();
        try {
            RssParser parser = new RssParser();
            for(int i = 0;i<RSS_FEEDS.length;i++)
            {
            	rssItems = parser.parse(getInputStream(RSS_FEEDS[i]));
            	rssAllItems.addAll(rssItems);
            }
            
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssAllItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }
 
    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
           
            return null;
        }
    }

	
}
