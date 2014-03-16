package dev.tech.budgetcalendar;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class EditTransactions extends Activity {

	private BudgetCalendarDay aDay;
	private EditText editTextTotal;
	private TextView txtTotalLabel;
	private ListView lstTransaction;
	private TransactionsAdapter transAdapter;
	private Button btnSave;
	static EditDayDialog editDDay = new EditDayDialog();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_day_totals);

		aDay = YearModel.getInstance().getCurrentMonth().getCurrentDay();

		final Spinner categortySpinner = (Spinner) findViewById(R.id.spinnerCategories);

		txtTotalLabel = (TextView) findViewById(R.id.txtTotalLabel);
		editTextTotal = (EditText) findViewById(R.id.editTextTotal);
		lstTransaction = (ListView) findViewById(R.id.lstTransactions);
		btnSave = (Button) findViewById(R.id.btnSave);

		YearModel.getInstance().editTransactionAct = this;
		loadTotalLabel();

		List<String> SpinnerArray = new ArrayList<String>();

		SpinnerArray.add("Gas");
		SpinnerArray.add("Groceries");
		SpinnerArray.add("Junk Food");
		SpinnerArray.add("Coffee");
		SpinnerArray.add("Alcohal");
		SpinnerArray.add("Add Category");

		transAdapter = new TransactionsAdapter(this, aDay.getTransactions());
		lstTransaction.setAdapter(transAdapter);

		CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this, R.layout.category_spinner_item, SpinnerArray, this.getLayoutInflater());

		categortySpinner.setAdapter(adapter);
		categortySpinner.setSelection(-1);

		editTextTotal.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					double newTransactionAmount = Double.valueOf(editTextTotal.getText().toString());
					double newTotal = aDay.getTotal() + newTransactionAmount;
					aDay.setTotal(newTotal);
					aDay.addTransaction(categortySpinner.getSelectedItem().toString(), newTransactionAmount);
					transAdapter.notifyDataSetChanged();
					txtTotalLabel.setText("-" + String.valueOf(newTotal));
					return true;
				}
				return false;
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				YearModel.getInstance().calendarGv.invalidateViews();
				finish();
			}
		});
	}

	public void loadTotalLabel() {
		this.txtTotalLabel.setText(String.valueOf(YearModel.getInstance().getCurrentMonth().getCurrentDay().getTotal()));
	}

	public class CategorySpinnerAdapter extends ArrayAdapter<String> {

		private LayoutInflater inflator;
		private List<String> categories;

		public CategorySpinnerAdapter(Context context, int textViewResourceId, List<String> spinnerArray, LayoutInflater inflator) {
			super(context, textViewResourceId, spinnerArray);
			this.categories = spinnerArray;
			this.inflator = inflator;
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@SuppressLint("NewApi")
		public View getCustomView(int position, View convertView, ViewGroup parent) {
			View row = inflator.inflate(R.layout.category_spinner_item, parent, false);
			TextView lacategoryLabel = (TextView) row.findViewById(R.id.category);
			if (position == categories.size() - 1) { 
				lacategoryLabel.setText(categories.get(position));
				lacategoryLabel.setTextColor(Color.LTGRAY);
			} else {
				ImageView ivImageIcon = (ImageView) row.findViewById(R.id.ivImageIcon);
				lacategoryLabel.setText(categories.get(position));  
				int resId = YearModel.getInstance().hmImages.get(categories.get(position)); 
				ivImageIcon.setImageResource(resId); 
			}
			return row;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_transactions, menu);
		return true;
	}
}
