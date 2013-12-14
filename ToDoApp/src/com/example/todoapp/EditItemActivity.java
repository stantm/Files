package com.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity
{
	private EditText etChangeItem;
	private String itemToEdit;
//	private int posHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		itemToEdit = getIntent().getStringExtra("todo_item");
//		posHolder = getIntent().getIntExtra("arry_pos", 0);
		
		etChangeItem = (EditText) findViewById(R.id.etChangeItem);
		etChangeItem.append(itemToEdit);
	}
	
	public void onSaveItem(View v)
	{
		Intent data = new Intent();
		data.putExtra("nItem", etChangeItem.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
