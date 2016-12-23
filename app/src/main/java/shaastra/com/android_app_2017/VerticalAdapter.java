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
    int themeres;
    String verticalname;
    int image;
    public VerticalAdapter(List<VerticalItem> list, int themeres, String verticalname,int image)
    {
        this.verticalList = list;
        this.themeres = themeres;
        this.verticalname = verticalname;
        this.image = image;
    }


    @Override
    public VerticalAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalAdapter.viewHolder holder, int position) {
        final VerticalItem i = verticalList.get(position);
        holder.iName.setText(i.getItemName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Context context = view.getContext();
                Intent intent = new Intent(context, EventsActivity.class);
                intent.putExtra("itemname", i.getItemName());
                intent.putExtra("itemid", i.getItemid());
                intent.putExtra("theme", themeres);
                intent.putExtra("verticalname", verticalname);
                intent.putExtra("image_name",image);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return verticalList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        public TextView iName;
        // public ImageView img;

        public viewHolder(View v)
        {
            super(v);
            iName = (TextView) v.findViewById(R.id.itemName);
            //img = (ImageView) v.findViewById(R.id.itemImage);
        }
    }
}