package in.gov.cbec;


import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import in.gov.cbec.customs.CustomsActsActivity;
import in.gov.cbec.dialogs.SimpleErrorAlertDialog;
import in.gov.cbec.util.BAMainActivityListAdapter;
import in.gov.cbec.util.CbecConstants;
import in.gov.cbec.util.CbecMessages;
import in.gov.cbec.util.CbecUtils;
import in.gov.cbec.util.DownloadFile;
import in.gov.cbec.util.RssAdapter;
import in.gov.cbec.util.RssItem;

public class CustomsMainActivity extends FragmentActivity implements OnItemClickListener, DialogInterface.OnClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.customsmain);
        ListView lv = (ListView)this.findViewById(R.id.cusMainListView);
       // setListAdapter(new ArrayAdapter<String>(this,
        	//	android.R.layout.simple_list_item_1, CbecConstants.CBEC_CUSTOMS_ACTIVITY_CATEGORIES));
        lv.setAdapter(new BAMainActivityListAdapter(this,CbecConstants.CBEC_CUSTOMS_ACTIVITY_CATEGORIES));
        lv.setOnItemClickListener(this);
    }
	
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch(position)
		{
			case 0:
				showCustomsActs();
				break;
			case 1:
				showCusRules();
				break;
			case 2:
				showCustomsNotifs();
				break;
			case 3:
				showCustomsCirculars();
				break;
			case 4:
				showTRGuide();
				break;
			case 5:
				showCusRegulations();
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
	public void showCusRules()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_RULES_LINK );
        this.startActivity(i);
	}
	public void showCusRegulations()
	{
		Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_REGS_LINK );
        this.startActivity(i);
	}
	public void showTRGuide()
	{
		/*Intent i = new Intent(this, CbecWebViewActivity.class);
		i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, CbecConstants.CBEC_WEB_CUSTOMS_TR_GUIDE_LINK );
        this.startActivity(i);*/
		String fileKey=CbecConstants.CBEC_CUSTOMS_MODULE+"_"+"3";
		String fileName=(String)CbecUtils.getFileNames().get(fileKey);
		boolean fileExists = CbecUtils.doesFileExist(fileName);
		if(!fileExists)
		{
			try
			{
				DownloadFile downloadFile = new DownloadFile(this,CbecConstants.CBEC_CUSTOMS_MODULE,fileKey,fileName);
				downloadFile.execute(new URL((String)CbecUtils.getFileURLs().get(fileKey)));
			}
			catch(MalformedURLException e)
			{
				//Log.e(CbecConstants.CBEC_ERR_MSG_TAG,e.getMessage());
			}
		}
		else //show the file from cbec root dir
		{
			boolean shown = CbecUtils.showFileOnSDCard(fileName, this);
			if(!shown)
			{
				if(CbecUtils.isOnline(this))
				{
					CbecUtils.showSDCardFileOnGoogleDocs(this, fileName);
				}
				else
				{
					DialogFragment newFragment = SimpleErrorAlertDialog.newInstance(R.drawable.alert,CbecMessages.CBEC_MSG_ERR,CbecMessages.CBEC_MSG_NO_INTERNET_ERR2);
				    newFragment.show(this.getSupportFragmentManager(), "dialog");
				}
			}
		}
	}


	@Override
	public void onClick(DialogInterface dialog, int arg1) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}

	

}
