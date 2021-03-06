package io.github.jkim3213.cowraiser;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter {

    List<StoreItem> storeItemList;

    public StoreAdapter(List<StoreItem> storeItemList) {
        this.storeItemList = storeItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        RecyclerView.ViewHolder vh = new StoreItemHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final StoreItem si = storeItemList.get(position);
        StoreItemHolder storeItemHolder = (StoreItemHolder) holder;
        storeItemHolder.itemName.setText(si.name);
        storeItemHolder.itemImage.setImageResource(si.imageId);
        //set on click for button
        storeItemHolder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout storeLayout = (ConstraintLayout) v.getParent().getParent().getParent().getParent();
                String toastMessage;
                Context context = storeLayout.getContext();
                if(UserProfile.ecoDollars >= si.cost) {
                    UserProfile.ecoDollars -= si.cost;
                    toastMessage = "Bought " + si.name + " for " + si.cost + " ecodollars.";
                    //TODO when we have a server, we request BUY
                    UserProfile.inventory.put(si.name, UserProfile.inventory.get(si.name) + 1);
                    TextView tv = storeLayout.findViewById(R.id.ecoDollars);
                    System.out.println(tv);
                    String updatedEco = context.getString(R.string.num_ecodollars, UserProfile.ecoDollars);
                    tv.setText(updatedEco);
                } else {
                    toastMessage = "Not enough ecodollars. Need " + (si.cost - UserProfile.ecoDollars) + " more ecodollars.";
                }
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return storeItemList.size();
    }

    public class StoreItemHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView itemName;
        ImageView itemImage;
        Button buyButton;

        public StoreItemHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemImage = itemView.findViewById(R.id.itemIcon);
            buyButton = itemView.findViewById(R.id.buyButton);

        }
    }
}
