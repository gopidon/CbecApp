package in.gov.cbec;

import in.gov.cbec.R;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

@SuppressWarnings("deprecation")
public class CbecMainActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cbecmain);
        
        TabHost tabHost = getTabHost();
        
        // Tab for Customs
        TabSpec cus_spec = tabHost.newTabSpec("Customs");
        // setting Title and Icon for the Tab
        View view = LayoutInflater.from(tabHost.getContext()).inflate(R.layout.cus_tab, null);
        cus_spec.setIndicator(view);
        Intent cus_intent = new Intent(this, CustomsMainActivity.class);
        cus_spec.setContent(cus_intent);
         
        // Tab for Excise
        TabSpec ex_spec = tabHost.newTabSpec("Excise");   
        view = LayoutInflater.from(tabHost.getContext()).inflate(R.layout.ex_tab, null);
        ex_spec.setIndicator(view);
        Intent ex_intent = new Intent(this, ExciseMainActivity.class);
        ex_spec.setContent(ex_intent);
         
        // Tab for Service Tax
        TabSpec st_spec = tabHost.newTabSpec("Service Tax");
        view = LayoutInflater.from(tabHost.getContext()).inflate(R.layout.st_tab, null);
        st_spec.setIndicator(view);
        Intent st_intent = new Intent(this, STMainActivity.class);
        st_spec.setContent(st_intent);
        
        TabSpec news_spec = tabHost.newTabSpec("Tax News");
        view = LayoutInflater.from(tabHost.getContext()).inflate(R.layout.news_tab, null);
        news_spec.setIndicator(view);
        Intent news_intent = new Intent(this, NewsActivity.class);
        news_spec.setContent(news_intent);
        
       
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(cus_spec); 
        tabHost.addTab(ex_spec); 
        tabHost.addTab(st_spec);
        tabHost.addTab(news_spec); 
        
        TabWidget tabWidget = tabHost.getTabWidget();

        for(int i=0; i<tabWidget.getChildCount(); i++)
          tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_bg);
        
    }

   
}
