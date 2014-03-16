package dev.tech.budgetcalendar;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class BudgetCalendar extends FragmentActivity {

	public CalendarGvAdapter adapter;
	private TextView tvMonth;
	Calendar cal = Calendar.getInstance();
	private Button btnLeft;
	private GridView gridview;
	private Button btnRight;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);

		YearModel.getInstance().currentMonthInt = getMonth();
		YearModel.getInstance().currentDaysInMonth = getDaysInMonth();
		YearModel.getInstance().currentYearInt = getYear();

		gridview = (GridView) findViewById(R.id.gridview);
		tvMonth = (TextView) findViewById(R.id.tvMonth);

		YearModel.getInstance().calendarGv = gridview;

		loadGrid();
		loadDateView();

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				YearModel.getInstance().currentDay = position;
				Intent editDay = new Intent(v.getContext(), EditTransactions.class);
				startActivity(editDay);
			}
		});

		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				YearModel.getInstance().decrementMonthInt();
				YearModel.getInstance().currentDaysInMonth = getDaysInMonth();
				loadGrid();
				tvMonth.setText(updateMonth());
				gridview.invalidateViews();
			}
		});

		btnRight = (Button) findViewById(R.id.btnRight);
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				YearModel.getInstance().incrementMonthInt();
				YearModel.getInstance().currentDaysInMonth = getDaysInMonth();
				loadGrid();
				tvMonth.setText(updateMonth());
				gridview.invalidateViews();
			}
		});
	}

	private void loadGrid() {
		adapter = new CalendarGvAdapter(this, R.layout.calendar_item, YearModel.getInstance().getCurrentMonth().days);
		gridview.setAdapter(adapter);
	}

	public int getMonth() {
		cal.setTime(new Date());
		int month = cal.get(Calendar.MONTH);
		return month;
	}

	public int getYear() {
		return cal.get(Calendar.YEAR);
	}

	public int getDaysInMonth() {
		Calendar mycal = new GregorianCalendar(getYear(), YearModel.getInstance().currentMonthInt, 1);
		return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public void loadDateView() {
		this.tvMonth.setText(getDate());
	}

	private String getDate() {
		return String.valueOf(new SimpleDateFormat("EEE, d MMM yyyy h:mm a").format(new Date()));
	}

	public String updateMonth() {
		return new DateFormatSymbols().getMonths()[YearModel.getInstance().currentMonthInt];
	}
}
