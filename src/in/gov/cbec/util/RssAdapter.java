package in.gov.cbec.util;

import java.util.List;

import in.gov.cbec.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RssAdapter extends BaseAdapter {
	 
    private final List<RssItem> items;
    private final Context context;
 
    public RssAdapter(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
    }
 
    public int getCount() {
        return items.size();
    }
 
    public Object getItem(int position) {
        return items.get(position);
    }
 
    public long getItemId(int id) {
        return id;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.rss_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemTitle.setText(items.get(position).getTitle());
        return convertView;
    }
 
    static class ViewHolder {
        TextView itemTitle;
    }

	
}