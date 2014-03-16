package dev.tech.budgetcalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class aMonth {

	public ArrayList<BudgetCalendarDay> days = new ArrayList<BudgetCalendarDay>();

	public void addAllDays(ArrayList<BudgetCalendarDay> daysOb) {
		days.addAll(daysOb);
	}

	public BudgetCalendarDay getDat(int key) {
		return days.get(key);
	}

	public void updateDay(BudgetCalendarDay value) {
		days.add(value);
	}

	public BudgetCalendarDay getCurrentDay() {
		BudgetCalendarDay d = days.get(YearModel.getInstance().currentDay);
		if (d == null) {
			d = new BudgetCalendarDay(YearModel.getInstance().currentDay);
			updateDay(d);
		}
		return d;
	}
}
