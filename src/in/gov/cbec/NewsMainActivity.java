package in.gov.cbec;

import in.gov.cbec.customs.CustomsActsActivity;
import in.gov.cbec.util.BAMainActivityListAdapter;
import in.gov.cbec.util.CbecConstants;
import in.gov.cbec.util.NewsMainActivityListAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NewsMainActivity extends FragmentActivity implements OnItemClickListener, DialogInterface.OnClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.newsmain);
        ListView lv = (ListView)this.findViewById(R.id.newsMainListView);
       // setListAdapter(new ArrayAdapter<String>(this,
        	//	android.R.layout.simple_list_item_1, CbecConstants.CBEC_CUSTOMS_ACTIVITY_CATEGORIES));
        lv.setAdapter(new NewsMainActivityListAdapter(this,CbecConstants.CBEC_NEWS_ACTIVITY_CATEGORIES));
        lv.setOnItemClickListener(this);
    }
	
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		showNews(position);
    }
	
	private void showNews(int position)
	{
		Intent i = new Intent(this, NewsActivity.class);
		i.putExtra("position", position);
	    this.startActivity(i);
	}
	
	public void onClick(DialogInterface dialog, int arg1) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}

}
