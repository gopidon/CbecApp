package in.gov.cbec.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class RefreshActsAndManuals extends AsyncTask<URL, Integer, String> {

	private HashMap<String, String> allFiles;
	private String fileName;
	private String fileURL;
	private Activity callingActivity;
	private MenuItem refresh;
	private ProgressDialog mProgressDialog;
	private String callingModule;
	
	public RefreshActsAndManuals(Activity act, MenuItem mItem, String module)
	{
		callingActivity = act;
		refresh = mItem;
		callingModule=module;
	}
	
	
	
	protected void onPreExecute()
	{
		mProgressDialog = new ProgressDialog(callingActivity);
		mProgressDialog.setMessage(CbecMessages.CBEC_MSG_REF_ACTS_MANUALS);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.show();
	}

	@Override
	protected String doInBackground(URL... params) {
		
		
		allFiles = getFileNamesAndURLs();
		
		for (Entry<String, String> entry : allFiles.entrySet()) {
		    fileName = entry.getKey();
		    fileURL = entry.getValue();
		    try {
		    	//mProgressDialog.setMessage("Updating "+fileName+" ...");
				downloadFileFromWebToSDCard(fileName,new URL(fileURL));
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Log.e(CbecConstants.CBEC_ERR_MSG_TAG,e.getMessage());
			}
        	if (isCancelled()) break;
		    // ...
		}
	   
	    return null;
	}
	
	protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    	super.onProgressUpdate(progress);
    	mProgressDialog.setMessage(CbecMessages.CBEC_MSG_UPDATING+fileName+"...");
        mProgressDialog.setProgress(progress[0]);
    }
	
	protected void onPostExecute(String result) {
		 mProgressDialog.setMessage(CbecMessages.CBEC_MSG_UPD_COMPLETE);
	     refresh.collapseActionView();
	     refresh.setActionView(null);
	     mProgressDialog.dismiss();
	}
	
	public HashMap getFileNamesAndURLs()
	{
		if(CbecConstants.CBEC_CUSTOMS_MODULE.equals(callingModule))
		{
			return CbecUtils.getCustomsFileNamesAndURLs();
		}
		else if(CbecConstants.CBEC_EXCISE_MODULE.equals(callingModule))
		{
			return CbecUtils.getExciseFileNamesAndURLs();
		}
		else
		{
			return CbecUtils.getSTFileNamesAndURLs();
		}
	}
	
	public void downloadFileFromWebToSDCard(String fileName,URL url)
    {
    	try{
    	//set the download URL, a url that points to a file on the internet
        //this is the file to be downloaded
       

        //create the new connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        //set up some things on the connection
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);

        //and connect!
        urlConnection.connect();

        //set the path where we want to save the file
        //in this case, going to save it on the root directory of the
        //sd card.
        File cbecRoot = CbecUtils.getCBECRoot();
        if(!cbecRoot.exists())
        {
        	cbecRoot.mkdir();
        }
        //create a new file, specifying the path, and the filename
        //which we want to save the file as.
        File file = new File(cbecRoot,fileName);
        

        //this will be used to write the downloaded data into the file we created
        FileOutputStream fileOutput = new FileOutputStream(file);

        //this will be used in reading the data from the internet
        InputStream inputStream = urlConnection.getInputStream();

        //this is the total size of the file
        int totalSize = urlConnection.getContentLength();
        //variable to store total downloaded bytes
        int downloadedSize = 0;

        //create a buffer...
        byte[] buffer = new byte[1024];
        int bufferLength = 0; //used to store a temporary size of the buffer

        //now, read through the input buffer and write the contents to the file
        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                //add the data in the buffer to the file in the file output stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                //add up the size so we know how much is downloaded
                downloadedSize += bufferLength;
                //this is where you would do something to report the prgress, like this maybe
               // updateProgress(downloadedSize, totalSize);
                publishProgress((int) ((downloadedSize * 100)/totalSize));

        }
        //close the output stream when done
        fileOutput.close();

//catch some possible errors...
    	} catch(Exception e) {
        e.printStackTrace();

    	
    }
}

}
