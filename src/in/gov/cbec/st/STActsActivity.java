package in.gov.cbec.st;

import in.gov.cbec.R;
import in.gov.cbec.util.CbecConstants;
import in.gov.cbec.util.CbecUtils;
import in.gov.cbec.util.DownloadFile;
import in.gov.cbec.util.RefreshActsAndManuals;

import java.net.MalformedURLException;
import java.net.URL;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class STActsActivity extends SherlockListActivity {
	private MenuItem menuItem;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, CbecConstants.CBEC_ST_ACTS));
    }
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.stactsmenu, menu);
	    return true;
	} 
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_refresh:
	      menuItem = item;
	      menuItem.setActionView(R.layout.progressbar);
	      menuItem.expandActionView();
	      RefreshActsAndManuals task = new RefreshActsAndManuals(this,menuItem);
			try {
				task.execute(new URL("http://www.cbec.gov.in"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Log.e(CbecConstants.CBEC_ERR_MSG_TAG,e.getMessage());
			}
	      break;
	    default:
	      break;
	    }
	    return true;
	  }
	
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
			//Toast.makeText(this,"You have selected " + actTypes[position],Toast.LENGTH_SHORT).show();
		showAct(position);
	}
	
	public void showAct(int position) {
		
		
		
		String fileKey=CbecConstants.CBEC_ST_MODULE+"_"+String.valueOf(position);
		String fileName=(String)CbecUtils.getFileNames().get(fileKey);
		boolean fileExists = CbecUtils.doesFileExist(fileName);
		if(!fileExists)
		{
			try
			{
				DownloadFile downloadFile = new DownloadFile(this,CbecConstants.CBEC_ST_MODULE,fileKey);
				downloadFile.execute(new URL((String)CbecUtils.getFileURLs().get(fileKey)));
			}
			catch(MalformedURLException e)
			{
				Log.e(CbecConstants.CBEC_ERR_MSG_TAG,e.getMessage());
			}
		}
		else //show the file from cbec root dir
		{
			CbecUtils.showFileOnSDCard(fileName, this);
		}
		
	}
}