package dev.tech.budgetcalendar;

import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.widget.GridView;

@SuppressLint("UseSparseArrays")
public class YearModel {

	public static YearModel currentMonth = new YearModel();

	public GridView calendarGv;
	public int currentYearInt;
	public int currentDaysInMonth;
	int currentMonthInt;
	int currentDay;

	public Map<Integer, aMonth> months = new HashMap<Integer, aMonth>();
	public HashMap<String, Integer> hmImages = new HashMap<String, Integer>();

	public EditTransactions editTransactionAct;

	public aMonth getMonth(int month) {
		return months.get(month);
	}

	public aMonth addMonth(int month, aMonth newMonth) {
		return months.put(month, newMonth);
	}

	public aMonth getCurrentMonth() {
		aMonth m = months.get(currentMonthInt);
		if (m == null) {
			m = new aMonth();
			// add one day for the blank
			m.updateDay(new BudgetCalendarDay(0));
			for (int i = 1; i < YearModel.getInstance().currentDaysInMonth + 1; i++) {
				m.updateDay(new BudgetCalendarDay(i));
			}
			addMonth(currentMonthInt, m);
		}
		return m;
	}

	private YearModel() {
		buildImages();
	}

	public int incrementMonthInt() {
		if (currentMonthInt < 11)
			this.currentMonthInt = currentMonthInt + 1;
		return currentMonthInt;
	}

	public int decrementMonthInt() {
		if (currentMonthInt > 0)
			this.currentMonthInt = currentMonthInt - 1;
		return currentMonthInt;
	}

	public static YearModel getInstance() {

		return currentMonth;
	}

	private void buildImages() {

		hmImages.put("Gas", R.drawable.gas);
		hmImages.put("Groceries", R.drawable.shopping_cart);
		hmImages.put("Junk Food", R.drawable.junk_food);
		hmImages.put("Coffee", R.drawable.coffee);
		hmImages.put("Alcohal", R.drawable.martini);
		hmImages.put("Movies", R.drawable.movies);

	}
}
