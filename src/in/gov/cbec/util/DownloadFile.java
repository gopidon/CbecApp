package in.gov.cbec.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

public class DownloadFile extends AsyncTask<URL, Integer, Long> {
	
	Activity callingActivity;
	ProgressDialog mProgressDialog;
	String fileName;
	String module;
	String fileKey;
	
	
	public DownloadFile(Activity act,String module, String fileKey, String fileName)
	{
		callingActivity = act;
		this.module=module;
		this.fileKey=fileKey;
		this.fileName=fileName;
	}
	protected void onPreExecute()
	{
		mProgressDialog = new ProgressDialog(callingActivity);
		mProgressDialog.setMessage(CbecMessages.CBEC_MSG_DOWNLOADING_FILE);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.show();
	}
	protected Long doInBackground(URL... urls) {
        int count = urls.length;
        
        
       // long totalSize = 0;
        for (int i = 0; i < count; i++) {
            
        	
        	downloadFileFromWebToSDCard(fileName,urls[i]);
        	
            if (isCancelled()) break;
        }
       // return totalSize;
        return Long.valueOf(0);
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    	super.onProgressUpdate(progress);
        mProgressDialog.setProgress(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    	mProgressDialog.dismiss();
    	CbecUtils.showFileOnSDCard(fileName, callingActivity);
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
