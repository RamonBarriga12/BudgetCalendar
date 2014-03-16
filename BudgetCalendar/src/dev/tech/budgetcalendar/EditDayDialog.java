package dev.tech.budgetcalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.tech.budgetcalendar.BudgetCalendarDay.Transaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class EditDayDialog extends DialogFragment {
	private static int dayNum;
	private BudgetCalendarDay aDay;
	private EditText editTextTotal;
	private TextView txtTotalLabel;
	private ArrayList<BudgetCalendarDay> transactions = new ArrayList<BudgetCalendarDay>();
	private ListView lstTransaction;
	private TransactionsAdapter transAdapter;
	private Button btnSave;
	private static int month;

	static EditDayDialog editDDay = new EditDayDialog();

	public EditDayDialog() {
		aDay = YearModel.getInstance().getCurrentMonth().getCurrentDay();
	}

	@SuppressLint("ValidFragment")
	static EditDayDialog newInstance() {
		return editDDay;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.edit_day_totals, container, false);
		final Spinner categortySpinner = (Spinner) v.findViewById(R.id.spinnerCategories);
		txtTotalLabel = (TextView) v.findViewById(R.id.txtTotalLabel);
		editTextTotal = (EditText) v.findViewById(R.id.editTextTotal);
		lstTransaction = (ListView) v.findViewById(R.id.lstTransactions);
		btnSave = (Button) v.findViewById(R.id.btnSave);
		List<String> SpinnerArray = new ArrayList<String>();

		SpinnerArray.add("Gas");
		SpinnerArray.add("Groceries");
		SpinnerArray.add("Junk Food");
		SpinnerArray.add("Coffee");
		SpinnerArray.add("Alcohal");
		SpinnerArray.add("Add Category");
		transAdapter = new TransactionsAdapter(this.getActivity(), aDay.getTransactions());
		lstTransaction.setAdapter(transAdapter);
		CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this.getActivity(), R.layout.category_spinner_item, SpinnerArray, inflater);

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
			}
		});

		return v;
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
}