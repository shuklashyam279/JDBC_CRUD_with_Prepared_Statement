package com.jspider.jdbc_crud_with_prepared_statement_controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

public class ProductController {

	// Method
	private static void createNewProduct(Connection connection, Scanner scanner, PreparedStatement ps) {
		System.out.print("Enter Product Name :  ");
		String name = scanner.next();
		System.out.print("Enter Product Color :  ");
		String color = scanner.next();
		System.out.print("Enter Product Price :  ");
		double price = scanner.nextDouble();
		System.out.println("Enter Product expiryDate : Ex.      2024 01 26  ");
		System.out.print("Enter Product Year :  ");
		int yyyy = scanner.nextInt();
		System.out.print("Enter Product Month :  ");
		int month = scanner.nextInt();
		System.out.print("Enter Product Day :  ");
		int day = scanner.nextInt();
		String insertQuery = "insert into product (name,color,price,expiryDate)values(?,?,?,?)";
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, name);
			ps.setString(2, color);
			ps.setDouble(3, price);
			ps.setObject(4, LocalDate.of(yyyy, month, day));
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println(rowsAffected + " Data Inserted Successfully.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method
	private static void deleteProduct(Connection connection, Scanner scanner, PreparedStatement ps) {
		System.out.print("Enter Product ID :  ");
		int pID = scanner.nextInt();
		String deleteQuery = "delete from product where id = ?";
		try {
			ps = connection.prepareStatement(deleteQuery);
			ps.setInt(1, pID);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println(rowsAffected + " Products Deleted Successfully. ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	// Method
	private static void displayAllProduct(Connection connection, PreparedStatement ps) {

		String displayQuery = "select * from product";
		try {
			ps = connection.prepareStatement(displayQuery);
			ResultSet resultSet = ps.executeQuery();
			
			System.out.println("Product :");
			System.out.println("+-----------+------------+----------+-------------+");
			System.out.println("|Product ID | Name       | Color    | Price       |");
			System.out.println("+-----------+------------+----------+-------------+");
			
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String color = resultSet.getString(3);
				float price = resultSet.getFloat(4);

				System.out.printf("| %-7d   | %-10s | %-9s| %-10.2f  |\n",id, name, color, price);
				System.out.println("+-----------+------------+----------+-------------+");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method
	private static void displayProductForID(Connection connection, Scanner scanner, PreparedStatement ps) {
		System.out.print("Enter Product ID to search  :  ");
		int pID = scanner.nextInt();
		String displayProductForID = "select * from product where id = ?;";
		
		try {
			ps = connection.prepareStatement(displayProductForID);
			ps.setInt(1, pID);
			ResultSet resultSet = ps.executeQuery();
			
			System.out.println("Product :");
			System.out.println("+-----------+------------+----------+-------------+");
			System.out.println("|Product ID | Name       | Color    | Price       |");
			System.out.println("+-----------+------------+----------+-------------+");
			
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String color = resultSet.getString(3);
				float price = resultSet.getFloat(4);

				System.out.printf("| %-7d   | %-10s | %-9s| %-10.2f  |\n",id, name, color, price);
				System.out.println("+-----------+------------+----------+-------------+");

			} else {
					System.out.println(pID +" Product ID doesn't exist.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	// Method
		private static void displayProductForID(Connection connection, PreparedStatement ps, int pID) {
			String displayProductForID = "select * from product where id = ?;";

			try {
				ps = connection.prepareStatement(displayProductForID);
				ps.setInt(1, pID);
				ResultSet resultSet = ps.executeQuery();

				if (resultSet.next()) {
				System.out.println("Product :");
				System.out.println("+-----------+------------+----------+-------------+");
				System.out.println("|Product ID | Name       | Color    | Price     |");
				System.out.println("+-----------+------------+----------+-------------+");
				
					int id = resultSet.getInt(1);
					String name = resultSet.getString(2);
					String color = resultSet.getString(3);
					float price = resultSet.getFloat(4);

					System.out.printf("| %-7d   | %-10s | %-9s | %-10.2f |\n",id, name, color, price);
					System.out.println("+-----------+------------+----------+-------------+");

				} else {
						System.out.println(pID +" Product ID doesn't exist.");
						updateProductForID(connection, new Scanner(System.in), ps);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	// Method
	private static void updateProductForID(Connection connection, Scanner scanner, PreparedStatement ps) {

		System.out.print("Enter Product ID to update Product  :  ");
		int pID = scanner.nextInt();
		System.out.println("");
		displayProductForID(connection,ps, pID);
		System.out.println("     1.Name       2.Color        3.Price");
		System.out.print("What do you want to update  :  ");
		System.out.println("");
		int option  = scanner.nextInt();


		try {
			switch (option) {
			case 1: {
							System.out.print("Enter new name  :  ");
							String rename = scanner.next();
							String updateName = "update product set name = ? where id = ?;";
							ps = connection.prepareStatement(updateName);
							ps.setString(1, rename);
							ps.setInt(2, pID);
							ps.executeUpdate();
					} break;
			case 2: {
							System.out.print("Enter new color  :  ");
							String rename = scanner.next();
							String updateColor = "update product set color = ? where id = ?;";
							ps = connection.prepareStatement(updateColor);
							ps.setString(1, rename);
							ps.setInt(2, pID);
							ps.executeUpdate();
				
					} break;
			case 3: {
							System.out.print("Enter new price  :  ");
							double reprice = scanner.nextDouble();
							String updatePrice = "update product set price = ? where id = ?;";
							ps = connection.prepareStatement(updatePrice);
							ps.setDouble(1, reprice);
							ps.setInt(2, pID);
							ps.executeUpdate();
				
					} break;
			default:
				
			}
//			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// Method
	public static void exit() throws InterruptedException {
		System.out.print("Exiting System");
		int i = 10;
		while (i != 0) {
			System.out.print(".");
			Thread.sleep(300);
			i--;
		}
		System.out.println();
		System.out.println("Thank You !!!");
		java.lang.System.exit(0);
	}

	// Main Method

	public static void main(String[] args) {
		
		final String url = "jdbc:mysql://127.0.0.1:3306/jspiders";
		final String userName = "root";
		final String password = "Password@1234";

		Scanner scanner = new Scanner(System.in);
		Connection connection = null;
		PreparedStatement ps = null;
		// STEP 1 : Registering the Driver class.
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
		// STEP 2 : Creating Connection
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while (true) {
			System.out.println();
			System.out.println("Choose Your Given  Options.  ");
			System.out.print(
					    "     1.  Create a new Product"
					+ "\n     2.  Delete a new Product"
					+ "\n     3.  Display All Products"
					+ "\n     4.  Display Specific Product"
					+ "\n     5.  Update Specific Product"
					+ "\n     6.  Exit\n");
			System.out.println("Enter Your Option   :   ");
			int option = scanner.nextInt();
			switch (option) {
			case 1:
				createNewProduct(connection, scanner, ps);
				break;
			case 2:
				deleteProduct(connection, scanner, ps);
				break;
			case 3:
				displayAllProduct(connection, ps);
				break;
			case 4:
				displayProductForID(connection,scanner,ps);
				break;
			case 5:
				updateProductForID(connection,scanner,ps);
				break;
			case 6:
				try {
					scanner.close();
					connection.close();
					exit();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		}
	}

}