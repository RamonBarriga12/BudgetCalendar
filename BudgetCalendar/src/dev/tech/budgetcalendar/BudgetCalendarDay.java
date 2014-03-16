package dev.tech.budgetcalendar;

import java.util.ArrayList;

public class BudgetCalendarDay {

	private int calendarDay;
	private double total;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

	public BudgetCalendarDay(int calendarDay) {
		this.calendarDay = calendarDay;
	}

	public double getTotal() {
		return total;
	}

	public void subTractFromTotal(double d) {
		this.total = total - d;
	}

	public void addToTotal(double d) {
		this.total = total + d;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCalendarDay() {
		return calendarDay;
	}

	public void setCalendarDay(int calendarDay) {
		this.calendarDay = calendarDay;
	}

	public void addTransaction(Transaction t) {
		transactions.add(t);
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;

	}

	public class Transaction {

		private int categoryCode;
		private double transactionTotal;
		private String categoryName;

		public Transaction(String categoryName, double transactionTotal) {
			this.categoryName = categoryName;
			this.transactionTotal = transactionTotal;
		}

		public int getCategoryCode() {
			return categoryCode;
		}

		public void setCategoryCode(int categoryCode) {
			this.categoryCode = categoryCode;
		}

		public double getTransactionTotal() {
			return transactionTotal;
		}

		public void setTotal(double transactionTotal) {
			this.transactionTotal = transactionTotal;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
	}

	public void addTransaction(String categoryName, double transactionTotal) {
		addTransaction(new Transaction(categoryName, transactionTotal));
	}
}
