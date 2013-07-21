package in.gov.cbec;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import in.gov.cbec.customs.CustomsActsActivity;
import in.gov.cbec.util.CbecConstants;

public class CustomsMainActivity extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, CbecConstants.CBEC_CUSTOMS_ACTIVITY_CATEGORIES));

    }
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		//Toast.makeText(this,"You have selected " + actTypes[position],Toast.LENGTH_SHORT).show();
		switch(position)
		{
			case 0:
				showCustomsActs();
				break;
			case 1:
				showCustomsNotifs();
				break;
			case 2:
				showCustomsCirculars();
				break;
			case 3:
				showBaggageRules();
				break;
			case 4:
				showTRGuide();
				break;
		}
			
	}
	
	public void showCustomsActs()
	{
		Intent i = new Intent(this, CustomsActsActivity.class);
		this.startActivity(i);
	}
	
	public void showCustomsActsWeb()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_ACTS_LINK );
        this.startActivity(i);
	}
	
	public void showCustomsNotifs()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_NOTIFS_LINK );
        this.startActivity(i);
	}
	
	public void showCustomsCirculars()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_CIRCULARS_LINK );
        this.startActivity(i);
	}
	public void showBaggageRules()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_BG_RULES_LINK );
        this.startActivity(i);
	}
	public void showTRGuide()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_TR_GUIDE_LINK );
        this.startActivity(i);
	}
}
