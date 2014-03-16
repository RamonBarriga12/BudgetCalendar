package dev.tech.budgetcalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.tech.budgetcalendar.BudgetCalendarDay.Transaction;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionsAdapter extends ArrayAdapter<Transaction> {
	private final Activity context;
	private ArrayList<Transaction> transactions;
	private TextView tvCategory;
	private TextView tvTransactionTotal;
	private Button btnRemove;
	private ImageView ivCategoryIcon;
	
	public TransactionsAdapter(Activity context, ArrayList<Transaction> transactions) {
		super(context, R.layout.transactions_item, transactions);
		this.transactions = transactions;
		this.context = context; 
	}



	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.transactions_item, null);
		}
		ivCategoryIcon = (ImageView) rowView.findViewById(R.id.ivCategoryIcon);
		tvCategory = (TextView) rowView.findViewById(R.id.tvCategory);
		tvTransactionTotal = (TextView) rowView.findViewById(R.id.tvTransactionTotal);
		btnRemove = (Button) rowView.findViewById(R.id.btnRemove);
		btnRemove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Transaction t = transactions.get(position);
				YearModel.getInstance().getCurrentMonth().getCurrentDay().subTractFromTotal(t.getTransactionTotal());
				YearModel.getInstance().editTransactionAct.loadTotalLabel();
				transactions.remove(position);
				notifyDataSetChanged();
			}
		});
		final Transaction t = transactions.get(position);
		int resId = (Integer) YearModel.getInstance().hmImages.get(t.getCategoryName()); 
		if(resId!=-1)
		ivCategoryIcon.setImageResource(resId);
		tvCategory.setText(t.getCategoryName());
		tvTransactionTotal.setText(String.valueOf(t.getTransactionTotal()));
		return rowView;
	}
}