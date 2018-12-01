package wpi.kgcm.hackforvenezuela;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostAdapter extends BaseAdapter {

    private ArrayList<Post> postList;
    private Context context;

    public PostAdapter(ArrayList<Post> list, Context cont) {
        this.postList = list;
        this.context = cont;
    }

    @Override
    public int getCount() {
        return this.postList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.postList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        private TextView title;
        private TextView author;
        private TextView time;
        private TextView body;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.row_layout, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.text_title);
            holder.author = (TextView) convertView.findViewById(R.id.text_author);
            holder.time = (TextView) convertView.findViewById(R.id.text_time);
            holder.body = (TextView) convertView.findViewById(R.id.text_body);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title = (TextView) convertView.findViewById(R.id.text_title);
        holder.author = (TextView) convertView.findViewById(R.id.text_author);
        holder.time = (TextView) convertView.findViewById(R.id.text_time);
        holder.body = (TextView) convertView.findViewById(R.id.text_body);

        Post post = postList.get(position);

        holder.title.setText(post.getTitle());
        holder.author.setText(post.getAuthor());
        Date pDate = new Date(post.getTimestamp());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.time.setText(dateFormat.format(pDate));
        holder.body.setText(post.getBody());

        return convertView;
    }


}