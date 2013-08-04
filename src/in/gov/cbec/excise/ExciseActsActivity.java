package in.gov.cbec.excise;

import in.gov.cbec.CbecWebViewActivity;
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


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExciseActsActivity extends SherlockListActivity {
	private MenuItem menuItem;
	private String module;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Slide;
        module=CbecConstants.CBEC_EXCISE_MODULE;
        setListAdapter(new ActsListAdapter(this,CbecConstants.CBEC_EXCISE_ACTS));
    }
	
	public String getModule()
	{
		return module;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.exactsmenu, menu);
	    return true;
	} 
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_refresh:
	      menuItem = item;
	      menuItem.setActionView(R.layout.progressbar);
	      menuItem.expandActionView();
	      RefreshActsAndManuals task = new RefreshActsAndManuals(this,menuItem,module);
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
		
		if(position==0)
		{
			Intent i = new Intent(this, CbecWebViewActivity.class);
			i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_EXCISE_ACTS_LINK );
	        this.startActivity(i);
		}
		
		else
		{
			String fileKey=CbecConstants.CBEC_EXCISE_MODULE+"_"+String.valueOf(position);
			String fileName=(String)CbecUtils.getExciseFileNames().get(fileKey);
			boolean fileExists = CbecUtils.doesFileExist(fileName);
			if(!fileExists)
			{
				try
				{
					DownloadFile downloadFile = new DownloadFile(this,CbecConstants.CBEC_EXCISE_MODULE,fileKey,fileName);
					downloadFile.execute(new URL((String)CbecUtils.getExciseFileURLs().get(fileKey)));
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
}
