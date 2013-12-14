package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity
{
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private int posHolder;
	
	private final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(todoAdapter);
		setupListViewListner();
	}

	private void setupListViewListner()
    {
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id)
            {
				posHolder = pos;  // hold position
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("todo_item", todoItems.get(pos));
				i.putExtra("arry_pos", pos);
				//
	            Log.e("Trace", "*********"  + todoItems.get(pos));
				
				startActivityForResult(i, REQUEST_CODE);            
            }
		});
		
	    lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id)
            {
				todoItems.remove(pos);
	            todoAdapter.notifyDataSetChanged();
	            writeItems();
	            return true;
            }    	
	    });

    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     String name = data.getExtras().getString("nItem");

	     todoItems.set(posHolder, name);
         todoAdapter.notifyDataSetChanged();
         writeItems();	     
	  }
	} 
	
	public void onAddedItem(View v)
	{
 		String itemText = etNewItem.getText().toString();
 		todoAdapter.add(itemText);
 		etNewItem.setText("");
 		writeItems();
	}
	
	private void readItems()
	{
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
		}
	}

	private void writeItems()
	{
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

}
