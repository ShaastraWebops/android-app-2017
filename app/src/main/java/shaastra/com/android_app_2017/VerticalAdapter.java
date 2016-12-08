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

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.viewHolder> {
    private List<VerticalItem> verticalList;
    public VerticalAdapter(List<VerticalItem> list)
    {
        this.verticalList = list;
    }


    @Override
    public VerticalAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalAdapter.viewHolder holder, int position) {
        VerticalItem i = verticalList.get(position);
        holder.iName.setText(i.getItemName());
    }

    @Override
    public int getItemCount() {
        return verticalList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView iName;
        // public ImageView img;

        public viewHolder(View v)
        {
            super(v);
            v.setOnClickListener(this);
            iName = (TextView) v.findViewById(R.id.itemName);
            //img = (ImageView) v.findViewById(R.id.itemImage);
        }

        @Override
        public void onClick(View view)
        {
            Context context = view.getContext();
            Intent i = new Intent(context, EventsActivity.class);
            i.putExtra("Event", iName.getText());
            view.getContext().startActivity(i);
        }
    }
}