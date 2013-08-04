package in.gov.cbec.st;

import in.gov.cbec.R;
import in.gov.cbec.util.ActsListAdapter;
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

import android.widget.ListView;

public class STActsActivity extends SherlockListActivity {
	private MenuItem refreshMenuItem;
	private String module;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Slide;
        module=CbecConstants.CBEC_ST_MODULE;
        setListAdapter(new ActsListAdapter(this,CbecConstants.CBEC_ST_ACTS));
    }
	
	public String getModule()
	{
		return module;
	}
	
	public MenuItem getRefreshMenuItem()
	{
		return refreshMenuItem;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.stactsmenu, menu);
	    return true;
	} 
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_refresh:
	      refreshMenuItem = item;
	      refreshMenuItem.setActionView(R.layout.progressbar);
	      refreshMenuItem.expandActionView();
	      RefreshActsAndManuals task = new RefreshActsAndManuals(this,refreshMenuItem,module);
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
		String fileName=(String)CbecUtils.getSTFileNames().get(fileKey);
		boolean fileExists = CbecUtils.doesFileExist(fileName);
		if(!fileExists)
		{
			try
			{
				DownloadFile downloadFile = new DownloadFile(this,CbecConstants.CBEC_ST_MODULE,fileKey,fileName);
				downloadFile.execute(new URL((String)CbecUtils.getSTFileURLs().get(fileKey)));
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
