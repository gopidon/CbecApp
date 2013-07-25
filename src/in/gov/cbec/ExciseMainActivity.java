package in.gov.cbec;

import in.gov.cbec.customs.CustomsActsActivity;
import in.gov.cbec.excise.ExciseActsActivity;
import in.gov.cbec.util.BAMainActivityListAdapter;
import in.gov.cbec.util.CbecConstants;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExciseMainActivity extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.ba_layout);
        //setListAdapter(new ArrayAdapter<String>(this,
        		//android.R.layout.simple_list_item_1, CbecConstants.CBEC_EXCISE_ACTIVITY_CATEGORIES));
        setListAdapter(new BAMainActivityListAdapter(this,CbecConstants.CBEC_EXCISE_ACTIVITY_CATEGORIES));
    }
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		//Toast.makeText(this,"You have selected " + actTypes[position],Toast.LENGTH_SHORT).show();
		switch(position)
		{
			case 0:
				showExciseActs();
				break;
			case 1:
				showExciseNotifs();
				break;
			case 2:
				showExciseCirculars();
				break;
		}
			
	}
	
	public void showExciseActs()
	{
		Intent i = new Intent(this, ExciseActsActivity.class);
		this.startActivity(i);
	}
	
	public void showExciseNotifs()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_EXCISE_NOTIFS_LINK );
        this.startActivity(i);
	}
	
	public void showExciseCirculars()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_EXCISE_CIRCULARS_LINK );
        this.startActivity(i);
	}
}
