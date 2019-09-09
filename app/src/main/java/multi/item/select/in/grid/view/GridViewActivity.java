package multi.item.select.in.grid.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {

    private Context context = GridViewActivity.this;
    private GridView gridview;
    private ArrayList<ItemModel> itemModelArrayList = new ArrayList<ItemModel>();
    private CustomBaseAdapter customBaseAdapter;
    boolean isMultiSelect = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initView();
        initObject();
        generateItemsList();
    }

    private void initView()
    {
        gridview=findViewById(R.id.grid_view);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
            {
                //Check if any items are already selected or not, If selected then set true
                isMultiSelect = customBaseAdapter.getSelectedItemCount() > 0;

                if (isMultiSelect)
                {
                    customBaseAdapter.checkCheckBox(position, !(customBaseAdapter.isSelected(position)));
                    int visiblePosition = gridview.getFirstVisiblePosition();
                    View itemView = gridview.getChildAt(position - visiblePosition);
                    gridview.getAdapter().getView(position, itemView, gridview);
                }
                else
                {
                    // there no selected items, finish the actionMode
                    Intent intent = new Intent(GridViewActivity.this, FullScreenActivity.class);
                    intent.putExtra("text",itemModelArrayList.get(position).getText());
                    intent.putExtra("image",itemModelArrayList.get(position).getImage());
                    startActivity(intent);
                }
            }
        });

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                isMultiSelect=true;
                customBaseAdapter.checkCheckBox(position, !(customBaseAdapter.isSelected(position)));
                int visiblePosition = gridview.getFirstVisiblePosition();
                View itemView = gridview.getChildAt(position - visiblePosition);
                gridview.getAdapter().getView(position, itemView, gridview);
                return true;
            }
        });
    }

    private void initObject()
    {
        itemModelArrayList = new ArrayList<>();
    }

    private void generateItemsList()
    {
        itemModelArrayList.add(new ItemModel("Pic 1",R.drawable.pic_1));
        itemModelArrayList.add(new ItemModel("Pic 2",R.drawable.pic_2));
        itemModelArrayList.add(new ItemModel("Pic 3",R.drawable.pic_3));
        itemModelArrayList.add(new ItemModel("Pic 4",R.drawable.pic_4));
        itemModelArrayList.add(new ItemModel("Pic 5",R.drawable.pic_5));
        itemModelArrayList.add(new ItemModel("Pic 6",R.drawable.pic_6));
        itemModelArrayList.add(new ItemModel("Pic 7",R.drawable.pic_7));
        itemModelArrayList.add(new ItemModel("Pic 8",R.drawable.pic_8));
        itemModelArrayList.add(new ItemModel("Pic 9",R.drawable.pic_9));

        customBaseAdapter = new CustomBaseAdapter(context, itemModelArrayList);
        gridview.setAdapter(customBaseAdapter);
    }

    //******* main menu **********
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_select_all:
                Toast.makeText(getApplicationContext(), "Select All", Toast.LENGTH_LONG).show();
                customBaseAdapter.selectAll();
                break;
            case R.id.action_deselect_all:
                Toast.makeText(getApplicationContext(), "Deselect All", Toast.LENGTH_LONG).show();
                customBaseAdapter.deselectAll();
                break;
            case R.id.action_delete_selected:
                Toast.makeText(getApplicationContext(), "Delete Selected", Toast.LENGTH_LONG).show();
                SparseBooleanArray selectedRowsDelete = customBaseAdapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRowsDelete.size() > 0)
                {
                    //Loop to all the selected rows array
                    for (int i = (selectedRowsDelete.size() - 1); i >= 0; i--) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRowsDelete.valueAt(i)) {

                            //remove the checked item
                            customBaseAdapter.removeItem(selectedRowsDelete.keyAt(i));
                            customBaseAdapter.notifyDataSetChanged();
                        }
                    }
                    //notify the adapter and remove all checked selection
                    customBaseAdapter.deselectAll();
                }
                break;
            case R.id.action_get_selected:
                SparseBooleanArray selectedRowsShow = customBaseAdapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRowsShow.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRowsShow.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRowsShow.valueAt(i)) {

                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            String selectedRowLabel = itemModelArrayList.get(selectedRowsShow.keyAt(i)).getText();

                            //append the row label text
                            stringBuilder.append(selectedRowLabel + "\n");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //******* main menu end **********
}
