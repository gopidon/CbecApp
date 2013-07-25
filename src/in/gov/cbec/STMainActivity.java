package in.gov.cbec;

import in.gov.cbec.customs.CustomsActsActivity;
import in.gov.cbec.st.STActsActivity;
import in.gov.cbec.util.BAMainActivityListAdapter;
import in.gov.cbec.util.CbecConstants;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class STMainActivity extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.ba_layout);
        //setListAdapter(new ArrayAdapter<String>(this,
        		//android.R.layout.simple_list_item_1, CbecConstants.CBEC_ST_ACTIVITY_CATEGORIES));
        setListAdapter(new BAMainActivityListAdapter(this,CbecConstants.CBEC_ST_ACTIVITY_CATEGORIES));
    }
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		//Toast.makeText(this,"You have selected " + actTypes[position],Toast.LENGTH_SHORT).show();
		switch(position)
		{
			case 0:
				showSTActs();
				break;
			case 1:
				showSTNotifs();
				break;
			case 2:
				showSTCirculars();
				break;
		}
			
	}
	
	public void showSTActs()
	{
		Intent i = new Intent(this, STActsActivity.class);
		this.startActivity(i);
	}
	
	public void showSTNotifs()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_ST_NOTIFS_LINK );
        this.startActivity(i);
	}
	
	public void showSTCirculars()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_ST_CIRCULARS_LINK );
        this.startActivity(i);
	}
}
