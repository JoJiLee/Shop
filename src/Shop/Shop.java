package project1;

import java.util.Scanner;

public class Shop {
	static double qualify = 0;
	static double discount = 0;
	//need the discounts to be accessible throughout code

	//thing to make numbers say 1st, 2nd, etc.
	public static String numSuffix(int i) {
		int rem = i % 10;
		switch (rem) { 
		case 0: 
		case 4: 
		case 5: 
		case 6: 
		case 7: 
		case 8: 
		case 9:
			return (i + "th"); 
		case 1:
			if (i % 100 != 11) 
				return (i + "st");
			else
				return (i + "th");
		case 2:
			if (i % 100 != 12)
				return (i + "nd"); 
			else
				return (i + "th");
		case 3:
			if (i % 100 != 13)
				return (i + "rd"); 
			else
				return (i + "th");
		default:
			break;
		}
		return "";
	}

	//set up shop
	public static int shop(String[] names, Scanner sc, double[] prices, int[] special) {
		System.out.println();

		System.out.print("Please enter the number of items to setup shop: ");
		int items = sc.nextInt();
		while (items <= 0) {
			System.out.println("You need items to set up a shop.");
			System.out.print("Please enter the number of items to setup shop: ");
			items = sc.nextInt();
		}
		System.out.println();

		for(int i = 1; i <= items; i++) {
			System.out.print("Enter the name of the " + numSuffix(i) + " product: ");
			names[i] = sc.next();
			System.out.print("Enter the per package price of " + names[i] + ": ");
			prices[i] = sc.nextDouble();
			while (prices[i] < 0) {
				System.out.print("Invalid input. Enter a value >= 0: ");
				prices[i] = sc.nextDouble();
			}
			System.out.print("Enter the number of packages ('x') to qualify for Special Discount (buy 'x' get 1 free) for " + names[i] + ", or 0 if no Special Discount offered: ");
			special[i] = sc.nextInt();
			while (special[i] < 0) {
				System.out.print("Invalid input. Enter a value >= 0: ");
				special[i] = sc.nextInt();
			}
		}

		System.out.println();
		System.out.print("Enter the dollar amount to qualify for Additional Discount (or 0 if none offered): "); 
		qualify = sc.nextDouble();
		while (qualify < 0) {
			System.out.print("Invalid input. Enter a value >= 0: ");
			qualify = sc.nextDouble();
		}
		if (qualify > 0) {
			System.out.print("Enter the Additional Discount rate (e.g., 0.1 for 10%): ");
			discount = sc.nextDouble();
			while (discount > 0.5 || discount <= 0) {
				System.out.print("Invalid input. Enter a value > 0 and <= 0.5: ");
				discount = sc.nextDouble();
			}
		}
		System.out.println();

		return items;
	}

	//buy items
	public static void Buy(Scanner sc, String[] names, double[] prices, int[] amounts, int items) {
		System.out.println();
		for(int i = 1; i <= items; i++) {
			System.out.print("Please enter the number of " + names[i] + " packages to buy: ");
			amounts[i] = sc.nextInt();
			while (amounts[i] < 0) {
				System.out.print("Invalid input. Enter a value >= 0: ");
				amounts[i] = sc.nextInt();
			}
		}
		System.out.println();
	}

	//list items
	public static void List(String[] names, double[] prices, int[] amounts, int items) {
		System.out.println();
		int amountTotal = 0;
		for(int i = 1; i <= items; i++) {
			amountTotal = amountTotal + amounts[i];
			if (amounts[i] > 0) {
				System.out.print(amounts[i] + " packages of " + names[i] + " @ $" + prices[i] + " per pkg = ");
				System.out.printf("$%.2f\n", (amounts[i] * prices[i]));
			}
			else if (amountTotal == 0) {
				while (amountTotal == 0) {
					System.out.println("No items were purchased.");
					amountTotal++;
				}
			}
		}
		System.out.println();
	}

	//checkout items
	public static void Checkout(double[] prices, int[] amounts, int[] special, int items, double[] disSpecial, Scanner sc, int set, int buy, int function) {
		System.out.println();
		double total = 0;	
		double totalSpecial = 0;

		//to calculate the special discount
		for (int i = 1; i < amounts.length; i++) {
			total = (prices[i] * amounts[i]) + total;
		}

		for (int i = 1; i <= items; i++) {
			if (special[i] != 0) {
				disSpecial[i] = amounts[i] / (special[i] + 1);
				totalSpecial = ((int)disSpecial[i] * prices[i]) + totalSpecial;
			}
		}

		double dis = 0;

		System.out.print("Original Sub Total: " + "\t\t"); //original sub total
		System.out.printf(" $%.2f\n", total);

		if (totalSpecial != 0) {
			System.out.print("Special Discounts: " + "\t\t"); //special discounts
			System.out.printf("-$%.2f\n", totalSpecial);
		}
		else
			System.out.println("No Special Discounts applied.");

		total = total - totalSpecial;
		System.out.print("New Sub Total: " + "\t\t\t"); //new sub total
		System.out.printf(" $%.2f\n", total);

		dis = total * discount;

		if (qualify == 0) {
			System.out.println("You did not qualify for an Additional Discount");
		}
		if (total >= qualify){
			System.out.print("Additional " + (discount * 100) + "% Discount: " + "\t"); //checking for additional discount
			System.out.printf("-$%.2f\n", dis);
		}

		total = total - dis;
		System.out.print("Final Sub Total: " + "\t\t"); //final sub total
		System.out.printf(" $%.2f\n", total);
		System.out.println();

		System.out.println("Thanks for coming!");
		System.out.println();
		System.out.println("-------------------------------------------------"); //restart program if needed
		System.out.print("Would you like to re-run (1 for yes, 0 for no)? ");
		int rerun = sc.nextInt();
		while (rerun < 0 || rerun > 1) {
			System.out.print("Invalid input. Please input 1 for yes, 0 for no: ");
			rerun = sc.nextInt();
		}
		if (rerun == 0) {
			System.out.println("-------------------------------------------------");
			System.exit(0);
		}
		if (rerun == 1) {
			System.out.println("-------------------------------------------------");
			System.out.println();
		}
	}

	//start
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		final int MAX = 1000;
		int set = 0;
		int function = 0;
		int items = 0;
		int buy = 0;

		String[] names = new String[MAX];
		double[] prices = new double[MAX];
		int[] amounts = new int[MAX];
		int[] special = new int[MAX];
		double[] disSpecial = new double[MAX];

		do {
			System.out.println("This program supports 4 functions:");
			System.out.println("\t" + "1. Set Up Shop");
			System.out.println("\t" + "2. Buy");
			System.out.println("\t" + "3. List Items");
			System.out.println("\t" + "4. Checkout");
			System.out.print("Please choose the function you want: ");
			function = sc.nextInt();

			if (1 <= function || function >= 4) {
				if (function == 1) { //set up
					set = 0;
					buy = 0;
					if (set == 0) {
						items = shop(names, sc, prices, special);
					}
					set = 1;
				}

				if (function == 2) { //buy
					if (set == 1) {
						Buy(sc, names, prices, amounts, items);
					}
					else {
						System.out.println();
						System.out.println("Shop is not set up yet!");
						System.out.println();
					}
					buy = 1;
				}

				if (function == 3) { //list
					if (set == 0) {
						System.out.println();
						System.out.println("Shop is not set up yet!");
						System.out.println();
					}
					else if (set == 1 && buy == 0) {
						System.out.println();
						System.out.println("No items were purchased.");
						System.out.println();
					}
					else if (buy == 1) {
						List(names, prices, amounts, items);
					}
				}

				if (function == 4) { //checkout
					if (set == 0) {
						System.out.println();
						System.out.println("Shop is not set up yet!");
						System.out.println();
					}
					else if (buy == 0) {
						System.out.println("No items were purchased.");
						continue;
					}
					else if (buy == 1) {
						Checkout(prices, amounts, special, items, disSpecial, sc, set, function, buy);
						set = 0;
						function = 0;
						items = 0;
						buy = 0;
					}
				}

				else if (function < 1 || function > 4){
					System.out.println("Invalid function.");
					System.out.print("Please choose the function you want 1-4: ");
				}
			}
		}
		while (true);
	}
}




