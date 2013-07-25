package in.gov.cbec.util;

import in.gov.cbec.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActsListAdapter extends BaseAdapter {

	private String[] data;
	private LayoutInflater mInflater;
	 
	public ActsListAdapter(Context context, String[] items) {
	    mInflater = LayoutInflater.from(context);
	    this.data = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
   	 
    	if (convertView == null) {
    	    convertView = mInflater.inflate(R.layout.actrow, null);
    	 
    	    holder = new ViewHolder();
    	    holder.text = (TextView) convertView.findViewById(R.id.content);
    	    convertView.setTag(holder);
    	} else {
    	    holder = (ViewHolder) convertView.getTag();
    	}
    	    // Bind the data efficiently with the holder.
    	    holder.text.setText(data[position]);
    	 
    	    //Set the background color depending of  odd/even colorPos result
    	    //int colorPos = position % colors.length;
    	    //convertView.setBackgroundColor(colors[colorPos]);
    	 
    	   return convertView;
    }
	
	
	static class ViewHolder {
	    TextView text;
	}
}
