package shaastra.com.android_app_2017;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gokulan on 27/11/16.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.viewHolder> {
    private List<EventListitem> verticalList;
    private Context context;
    private int[] imageresources;
    public EventListAdapter(List<EventListitem> list, Context context, int[] imageresources)
    {
        this.verticalList = list;
        this.context = context;
        this.imageresources = imageresources;
    }


    @Override
    public EventListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.viewHolder holder, final int position) {
        final EventListitem i = verticalList.get(position);
//        holder.title1.setText(i.getItemName());
        holder.imageview.setImageDrawable(context.getResources().getDrawable(imageresources[position]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), VerticalActivity.class);
                intent.putExtra("listname", i.getItemName());
                intent.putExtra("listid", i.getItemid());
                intent.putExtra("image",imageresources[position]);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return verticalList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public ImageView imageview;

        public viewHolder(View v)
        {
            super(v);
            imageview = (ImageView)itemView.findViewById(R.id.imageView);
            //img = (ImageView) v.findViewById(R.id.itemImage);
        }
    }
}