package dev.tech.budgetcalendar;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CalendarGvAdapter extends ArrayAdapter<BudgetCalendarDay> {

	private List<BudgetCalendarDay> calendar;
	Context context;

	public CalendarGvAdapter(Context context, int textViewResourceId, List<BudgetCalendarDay> calendar) {
		super(context, textViewResourceId, calendar);
		this.calendar = calendar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView = null;
		TextView tvTotals = null;
		final BudgetCalendarDay day = calendar.get(position);

		if (convertView == null) {
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.calendar_item, null);
		}

		tvTotals = (TextView) v.findViewById(R.id.tvTotals);
		dayView = (TextView) v.findViewById(R.id.date);

		if (position != 0) {
			dayView.setText(String.valueOf(position));
			if (day != null) {
				tvTotals.setText(String.valueOf(day.getTotal()));
				if (Double.valueOf(day.getTotal()) > 0) {
					tvTotals.setTextColor(Color.RED);
				}
			}
		}
		return v;
	}
}
