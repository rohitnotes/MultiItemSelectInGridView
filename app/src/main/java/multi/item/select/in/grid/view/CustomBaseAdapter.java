package multi.item.select.in.grid.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    private static final String TAG = CustomBaseAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<ItemModel> itemModelList;
    private LayoutInflater layoutInflater;
    private SparseBooleanArray selectedItemPosition;

    public CustomBaseAdapter(Context context, ArrayList<ItemModel> itemModels) {
        this.context = context;
        this.itemModelList = itemModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        selectedItemPosition = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return (null != itemModelList ? itemModelList.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return (null != itemModelList ? itemModelList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder itemViewHolder;

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
            itemViewHolder = new ItemViewHolder(convertView);
            convertView.setTag(itemViewHolder);
        }
        else
        {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        ItemModel rowItem = (ItemModel) getItem(position);
        ((ItemViewHolder) itemViewHolder).setData(rowItem,position);
        return convertView;
    }

    /**
     * Select all checkbox
     **/
    public void selectAll()
    {
        for (int i = 0; i < itemModelList.size(); i++)
        {
            checkCheckBox(i, true);
        }
        Log.d(TAG, "selectAll() : " + itemModelList);
        notifyDataSetChanged();
    }

    /**
     * Remove all checkbox
     **/
    public void deselectAll()
    {
        Log.d(TAG, "deselectAll() : " + itemModelList);
        selectedItemPosition.clear();
        notifyDataSetChanged();
    }

    /**
     * Check the Checkbox if not checked, if already check then unchecked
     **/
    public void checkCheckBox(int position, boolean value)
    {
        if (value)
        {
            selectedItemPosition.put(position, true);
        }
        else
        {
            selectedItemPosition.delete(position);
        }
        Log.d(TAG, "selectAll() : " +selectedItemPosition);
       // notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox position
     **/
    public SparseBooleanArray getSelectedIds()
    {
        return selectedItemPosition;
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    public int getSelectedItemCount()
    {
        return selectedItemPosition.size();
    }

    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems()
    {
        List<Integer> items = new ArrayList<>(selectedItemPosition.size());

        for (int i = 0; i < selectedItemPosition.size(); ++i)
        {
            items.add(selectedItemPosition.keyAt(i));
        }
        return items;
    }

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position)
    {
        return getSelectedItems().contains(position);
    }

    public void removeItem(int position)
    {
        itemModelList.remove(position);
    }

    private void removeRange(int positionStart, int itemCount)
    {
        for (int i = 0; i < itemCount; ++i)
        {
            itemModelList.remove(positionStart);
        }
        notifyDataSetChanged();
    }

    private class ItemViewHolder
    {
        FrameLayout frameLayout;
        TextView itemTextView;
        ImageView itemImageView;
        View view;

        public ItemViewHolder(View item)
        {
            frameLayout=item.findViewById(R.id.frame_layout);
            itemImageView = item.findViewById(R.id.image_view);
            itemTextView = item.findViewById(R.id.text_view);
            view = item.findViewById(R.id.alpha_view);
        }

        public void setData(ItemModel itemPosition,int position)
        {
            itemTextView.setText(itemPosition.getText());
            itemImageView.setImageResource(itemPosition.getImage());

            if(isSelected(position))
            {
                view.setAlpha(0.5f);
                frameLayout.setForeground(context.getResources().getDrawable(R.drawable.ic_done_black_24dp));
            }
            else
            {
                view.setAlpha(0.0f);
                frameLayout.setForeground(null);
            }
        }
    }
}
