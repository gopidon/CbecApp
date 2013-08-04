package in.gov.cbec.util;


import in.gov.cbec.CbecWebViewActivity;
import in.gov.cbec.R;
import in.gov.cbec.dialogs.SimpleErrorAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

public class CbecUtils {
	public static boolean isOnline(Activity act) {
		ConnectivityManager cm = (ConnectivityManager) act
		.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		 
		if (ni != null && ni.isConnected())
		  return true;
		else
		  return false;
	}
	
	public static File getCBECRoot()
	{
		File SDCardRoot = Environment.getExternalStorageDirectory();
        String sdCardRoot = SDCardRoot.getAbsolutePath();
        File cbecRoot = new File(sdCardRoot+"//"+CbecConstants.CBEC_DIR);
        return cbecRoot;
	}
	
	public static boolean doesFileExist(String fileName){
		File file = new File(getCBECRoot(),fileName);
		if(file.exists())
			return true;
		else
			return false;
	}
	
public static void showAssetsFile(Activity act, String fileName) {
	    File SDCardRoot = Environment.getExternalStorageDirectory();
	    String sdCardRoot = SDCardRoot.getAbsolutePath();
        File fileBrochure = new File(sdCardRoot+fileName);
        if (!fileBrochure.exists())
        {
             CopyAssetsbrochure(act, fileName);
        } 

        /** PDF reader code */
        
        File file = new File(sdCardRoot+fileName);        

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try 
        {
            act.startActivity(intent);
        } 
        catch (ActivityNotFoundException e) 
        {
             Toast.makeText(act, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
        }
    }

    
    
    private static void CopyAssetsbrochure(Activity act, String fileStr) {
    	File SDCardRoot = Environment.getExternalStorageDirectory();
    	String sdCardRoot = SDCardRoot.getAbsolutePath();
        AssetManager assetManager = act.getAssets();
        String[] files = null;
        try 
        {
            files = assetManager.list("");
        } 
        catch (IOException e)
        {
            //Log.e("tag", e.getMessage());
        }
        for(int i=0; i<files.length; i++)
        {
            String fStr = files[i];
            if(fStr.equalsIgnoreCase(fileStr))
            {
                InputStream in = null;
                OutputStream out = null;
                try 
                {
                  in = assetManager.open(files[i]);
                  out = new FileOutputStream(sdCardRoot + files[i]);
                  copyFile(in, out);
                  in.close();
                  in = null;
                  out.flush();
                  out.close();
                  out = null;
                  break;
                } 
                catch(Exception e)
                {
                   // Log.e(CbecConstants.CBEC_ERR_MSG_TAG, e.getMessage());
                } 
                
            }
        }
    }
    
    
    
    public static boolean showFileOnSDCard(String fileName, Activity act)
    {
    	File SDCardRoot = Environment.getExternalStorageDirectory();
    	String sdCardRoot = SDCardRoot.getAbsolutePath();
        File cbecRoot = new File(sdCardRoot+"//"+CbecConstants.CBEC_DIR);
        File file = new File(cbecRoot,fileName);        

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try 
        {
            act.startActivity(intent);
            //throw new ActivityNotFoundException();
        } 
        catch (ActivityNotFoundException e) 
        {
             Toast.makeText(act, CbecMessages.CBEC_MSG_NO_PDF_VWR_ERR, Toast.LENGTH_SHORT).show();
             return false;
        }
        catch(Exception e)
        {
        	Toast.makeText(act,CbecMessages.CBEC_MSG_UFSEEN_ERR,Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    
    public static void showSDCardFileOnGoogleDocs(Activity act,String fileName)
    {
    	    Intent i = new Intent(act, CbecWebViewActivity.class);
	    	HashMap<String, String> files = CbecUtils.getAllFilesAndURLs();
	    	String url = files.get(fileName);
	    	String gUrl = CbecConstants.CBEC_PREFIX_GDOCS_LINK + url;
			i.putExtra(CbecConstants.CBEC_WEB_SHOW_LINK, gUrl);
	        act.startActivity(i);
    	
    }

 private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }
 
 
 
 public static HashMap<String, String> getFileNames()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("CUSTOMS_0","customs_act.pdf");
		map.put("CUSTOMS_1","customs_manual.pdf");
		map.put("CUSTOMS_2","customs_tariff.pdf");
		map.put("CUSTOMS_3","customs_tr_guide.pdf");
		
		map.put("EXCISE_0","excise_act.pdf");
		
		map.put("ST_0","st_act.pdf");
		
		
		return map;
 }
 
 public static HashMap<String, String> getFileURLs()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("CUSTOMS_0","http://www.cbec.gov.in/customs/cs-act/custom-act-1962.pdf");
		map.put("CUSTOMS_1","http://www.cbec.gov.in/cs-manual-2012.pdf");
		map.put("CUSTOMS_2","http://www.cbec.gov.in/customs/cst2012-13/cst-act-1213.pdf");
		map.put("CUSTOMS_3","http://www.cbec.gov.in/trvler-guide_ason22may2013.pdf");
		
		//map.put("EXCISE_0","http://cestat.gov.in/CENTRAL EXCISE ACT.pdf");
		
		map.put("ST_0","http://www.servicetax.gov.in/st-act-upd-dec10.pdf");
		return map;
 }
 
 public static HashMap<String, String> getAllFilesAndURLs()
 {
	 HashMap<String, String> map = new HashMap<String, String>();
		map.put("customs_act.pdf","http://www.cbec.gov.in/customs/cs-act/custom-act-1962.pdf");
		map.put("customs_manual.pdf","http://www.cbec.gov.in/cs-manual-2012.pdf");
		map.put("customs_tariff.pdf","http://www.cbec.gov.in/customs/cst2012-13/cst-act-1213.pdf");
		map.put("customs_tr_guide.pdf","http://www.cbec.gov.in/trvler-guide_ason22may2013.pdf");
		
		//map.put("excise_act.pdf","http://cestat.gov.in/CENTRAL EXCISE ACT.pdf");
		
		map.put("st_act.pdf","http://www.servicetax.gov.in/st-act-upd-dec10.pdf");
		return map;
 }
 
 public static HashMap<String, String> getCustomsFileNames()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("CUSTOMS_0","customs_act.pdf");
		map.put("CUSTOMS_1","customs_manual.pdf");
		map.put("CUSTOMS_2","customs_tariff.pdf");
		map.put("CUSTOMS_3","customs_tr_guide.pdf");
		
		
		
		return map;
 }
 
 
 
 public static HashMap<String, String> getCustomsFileURLs()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("CUSTOMS_0","http://www.cbec.gov.in/customs/cs-act/custom-act-1962.pdf");
		map.put("CUSTOMS_1","http://www.cbec.gov.in/cs-manual-2012.pdf");
		map.put("CUSTOMS_2","http://www.cbec.gov.in/customs/cst2012-13/cst-act-1213.pdf");
		map.put("CUSTOMS_3","http://www.cbec.gov.in/trvler-guide_ason22may2013.pdf");
		
		
		return map;
 }
 
 public static HashMap<String, String> getCustomsFileNamesAndURLs()
 {
	 HashMap<String, String> map = new HashMap<String, String>();
		map.put("customs_act.pdf","http://www.cbec.gov.in/customs/cs-act/custom-act-1962.pdf");
		map.put("customs_manual.pdf","http://www.cbec.gov.in/cs-manual-2012.pdf");
		map.put("customs_tariff.pdf","http://www.cbec.gov.in/customs/cst2012-13/cst-act-1213.pdf");
		map.put("customs_tr_guide.pdf","http://www.cbec.gov.in/trvler-guide_ason22may2013.pdf");
		return map;
 }
 
 public static HashMap<String, String> getExciseFileNames()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("EXCISE_0","excise_act.pdf");
		return map;
 }
 
 
 
 public static HashMap<String, String> getExciseFileURLs()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		//map.put("EXCISE_0","http://cestat.gov.in/CENTRAL EXCISE ACT.pdf");
		return map;
 }
 
 public static HashMap<String, String> getExciseFileNamesAndURLs()
 {
	 HashMap<String, String> map = new HashMap<String, String>();
	//map.put("excise_act.pdf","http://cestat.gov.in/CENTRAL EXCISE ACT.pdf");
	return map;
 }
 
 public static HashMap<String, String> getSTFileNames()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("ST_0","st_act.pdf");
		return map;
 }
 
 
 
 public static HashMap<String, String> getSTFileURLs()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
		map.put("ST_0","http://www.servicetax.gov.in/st-act-upd-dec10.pdf");
		return map;
 }
 
 public static HashMap<String, String> getSTFileNamesAndURLs()
 {
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("st_act.pdf","http://www.servicetax.gov.in/st-act-upd-dec10.pdf");
		return map;
 }
}
