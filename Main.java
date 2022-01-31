import java.io.IOException;
import java.util.Scanner;



public class Main {

	public static void main(String[] args) {
		
		Player player1 = new Player("Player 1");
		Player player2 = new Player("Player 2");
		player1.inicialField();
		promptEnterKey();
		player2.inicialField();
		promptEnterKey();
		startGame(player1, player2);

	}
	
	public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	public static void startGame(Player player1, Player player2) {
		
		System.out.println("The game starts!");
		System.out.println();
		
		while(true) {
			
			if (gameStart(player1, player2) == 1) {
				return;
			}
			promptEnterKey();
			
			
			if (gameStart(player2, player1) == 1) {
				return;
			}
			promptEnterKey();
			
		}
		
		
		
	}
	
	public static int gameStart(Player playerOne, Player playerTwo) {
		
		Scanner scanner = new Scanner(System.in);
		
		String message = "Take a shot!";
		
//		System.out.println();
		
		playerTwo.printField(1);
		System.out.println("---------------------");
		playerOne.printField();
		System.out.println();
		System.out.println(playerOne.name + ", it's your turn:");
		System.out.println();
		System.out.print("> ");
		
		int ch = -1;
				
		while (true) {
			
			if (ch == 0 || ch == 2 || ch == 3 || ch == 4) {
				
//				System.out.println();
				System.out.println(message);
				
				if (ch == 4) {					
					return 1;				
				}
				
				return 0;
				
				
			} else if (ch == 1) {
				System.out.println();
				System.out.println(message);
				System.out.println();
				System.out.print("> ");
			}
			
			String s1 = "";
			
			int yyy = 0;
			
			while (yyy == 0) {
				
				if (scanner.hasNext()) {
					s1 = scanner.next();
					
					yyy = 1;
		        } else {	            
		        	scanner.next(); // -->important	            
		        }
				
			}			
			
			System.out.println();			
			
			ch = hit(playerTwo, s1);
			
			//0 - You hit a ship!
			//1 - Error! You entered the wrong coordinates! Try again:
			//2 - You missed!
			//3 - You sank a ship
			//4 - Game over
			
			switch (ch) {	
			case 0:
				message = "You hit a ship!";
				break;			
			case 1:
				message = "Error! You entered the wrong coordinates! Try again:";
				break;
			case 2:
				message = "You missed!";
				break;
			case 3:
				message = "You sank a ship!";
				break;	
			case 4:
				message = playerOne.name + ". You sank the last ship. You won. Congratulations!";
				break;	
				
			}
		}	
		
		
	}
	
	
	
	
	public static int hit(Player player, String s1) {
		
		char cnum1S1 = s1.charAt(0);
		String snum2S1 = s1.substring(1, s1.length());
				
		if (cnum1S1 < 'A' || cnum1S1 > 'J') {
//			System.out.println("Input Error");
			return 1;
		}
		
		int num1S1 = cnum1S1 - 65;
		
		
		
		int num2S1 = -1;
		
		
		try {
			num2S1 = Integer.parseInt(snum2S1);
			
			
		} catch (Exception e) {
//			System.out.println("Input Error");
			return 1;
		}
		
		if (num2S1 < 1 || num2S1 > 10) {
//			System.out.println("Input Error");
			return 1;						
		}
		
		num2S1 = num2S1 - 1;
		
		
		
//		System.out.println(num1S1);
//
//		System.out.println(num2S1);

		
		if (player.field[num1S1][num2S1].equals("~")) {
			player.field[num1S1][num2S1] = "M";
			return 2;
		}
		
		if (player.field[num1S1][num2S1].equals("X")) {
			return 0;
		}
		
		if (player.field[num1S1][num2S1].equals("M")) {
			return 2;
		}
		
		
		
		if (player.field[num1S1][num2S1].equals("O")) {
			player.field[num1S1][num2S1] = "X";
			
			int flag = checkSank(player, num1S1, num2S1);
			
			if (flag == 1) {
				if (gameOver(player.field) == 0) {
					return 4; //game over
				} else {
					return 3; //sank ship
				}
			}
	
			return 0;
		}
		
		return 0;
	}
	
	public static int checkSank(Player player, int x, int y) {
		
		if (checkSankShip(player.carrier, x, y) == 1) {
			return 1;
		}
		if (checkSankShip(player.battleship, x, y) == 1) {
			return 1;
		}
		if (checkSankShip(player.submarine, x, y) == 1) {
			return 1;
		}
		if (checkSankShip(player.cruiser, x, y) == 1) {
			return 1;
		}
		if (checkSankShip(player.destroyer, x, y) == 1) {
			return 1;
		}
		return 0;
			
	}
	
	public static int checkSankShip(int [][] arr, int x, int y) {
		
		for (int i = 0; i < arr[0].length; i++) {
			if (arr[0][i] == x && arr[1][i] == y) {
				
				arr[0][i] = -1;
				arr[1][i] = -1;
				
				for (int j = 0; j < arr[0].length; j++) {
					if (arr[0][j] != -1) {
						return 0;						
					}
				}
				return 1;
			}
		}		
		
		return 0;	
		
		
	}
	
	
	public static int gameOver (String[][] field) {
		
		int flag = 0;		
		for (int i = 0; i < field.length; i++) {			
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j].equals("O")) {
					flag = 1;
					return flag;
				}
			}
			
		}
		return flag;
		
	}
}

class Player {
	
	public int[][] carrier;		
	public int[][] battleship;
	public int[][] submarine;
	public int[][] cruiser;
	public int[][] destroyer;
	
	public String name;
	String[][] field;
	
	
	
	
	public Player(String name) {
		
		this.name = name;
		
		this.carrier = new int[][] {
			{-2, -2, -2, -2, -2},
			{-2, -2, -2, -2, -2}
		};
		
		this.battleship = new int[][] {
			{-2, -2, -2, -2},
			{-2, -2, -2, -2}
		};
		
		this.submarine = new int[][] {
			{-2, -2, -2},
			{-2, -2, -2}
		};
		
		this.cruiser = new int[][] {
			{-2, -2, -2},
			{-2, -2, -2}
		};
		
		this.destroyer = new int[][] {
			{-2, -2},
			{-2, -2}
		};
		
		field = new String[][] {
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
			{"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
		};
		
	}
	
	public void inicialField () {
		
		System.out.println(this.name + ", place your ships on the game field");
		System.out.println();
		printField();				
		fillField ("Aircraft Carrier", 5);
		printField();				
		fillField ("Battleship", 4);
		printField();				
		fillField ("Submarine", 3);
		printField();				
		fillField ("Cruiser", 3);
		printField();				
		fillField ("Destroyer", 2);
		printField();
		System.out.println();
		
	}
	
	public void fillField (String name, int col) {
		
		Scanner scanner = new Scanner(System.in);
		
		String message = "Enter the coordinates of the " + name + " (" + col + " cells):";
		
				
		while (true) {
			
			System.out.println();
			System.out.println(message);
			System.out.println();
			System.out.print("> ");
			
			String s1 = "";
			String s2 = "";
//			scanner.nextLine();
			
			int yyy = 0;
			
			while (yyy == 0) {
				if (scanner.hasNext()) {
					s1 = scanner.next();
					s2 = scanner.next();
					yyy = 1;
		        } else {	            
		        	scanner.next(); // -->important	            
		        }
				
			}
			

			
			System.out.println();			
			
			int ch = check(s1, s2, col);
			
			//0 - ok
			//1 - Input Error
			//2 - Wrong length
			//3 - Wrong ship location
			//4 - You placed it too close to another one
			
			
			if (ch == 0) {
				break;
			}
			
			
			switch (ch) {			
			case 1:
				message = "Error! Input Error! Try again:";
				break;
			case 2:
				message = "Error! Wrong length of the " + name + "! Try again:";
				break;
			case 3:
				message = "Error! Wrong ship location! Try again:";
				break;
			case 4:
				message = "Error! You placed it too close to another one. Try again:";
				break;
				
			}
			
		}	
		
	}
	
	public int check(String s1, String s2, int col) {
		
		char cnum1S1 = s1.charAt(0);
		String snum2S1 = s1.substring(1, s1.length());
		
		char cnum1S2 = s2.charAt(0);
		String snum2S2 = s2.substring(1, s2.length());
		
				
		if (cnum1S1 < 'A' || cnum1S1 > 'J' || cnum1S2 < 'A' || cnum1S2 > 'J') {
//			System.out.println("Input Error");
			return 1;
		}
		
		int num1S1 = cnum1S1 - 65;
		int num1S2 = cnum1S2 - 65;
		
		
		int num2S1 = -1;
		int num2S2 = -1;
		
		try {
			num2S1 = Integer.parseInt(snum2S1);
			num2S2 = Integer.parseInt(snum2S2);
			
		} catch (Exception e) {
//			System.out.println("Input Error");
			return 1;
		}
		
		if (num2S1 < 1 || num2S1 > 10 || num2S2 < 1 || num2S2 > 10) {
//			System.out.println("Input Error");
			return 1;						
		}
		
		num2S1 = num2S1 - 1;
		num2S2 = num2S2 - 1;
		
		
		
		if(num1S1 > num1S2) {
			int temp = num1S1;
			num1S1 = num1S2;
			num1S2 = temp;
		}
		
		if(num2S1 > num2S2) {
			int temp = num2S1;
			num2S1 = num2S2;
			num2S2 = temp;
		}
		


		
		//check length
		if (num1S1 == num1S2) {
			int length = num2S2 - num2S1;
			if (length != col -1) {
//				System.out.println("Wrong length1");
				return 2;
			}
		} else if (num2S2 == num2S1) {
			int length = num1S2 - num1S1;
			if (length != col -1) {
//				System.out.println("Wrong length2");
				return 2;
			}
		} else {
//			System.out.println("Wrong ship location");
			return 3;
		}
		
		
		//check if the cells are occupied
		int check1 = 0;
		for (int i = num1S1; i <= num1S2; i++) {
			for (int j = num2S1; j <= num2S2; j++) {
				if (!this.field[i][j].equals("~")) {
					check1 = 1;
				}
//				if (field[i][j].equals("~")) {
//					field[i][j] = "O";
//				}
			}			
		}
		
		if(check1 == 1) {
//			System.out.println("Wrong ship location");
			return 3;
		}
		
		//checking near cells
		int check2 = 0;
		for (int i = num1S1; i <= num1S2; i++) {
			for (int j = num2S1; j <= num2S2; j++) {
				int k = (i - 1 >= 0) ? (i - 1) : i;
				int k1 = (i + 1 <= 9) ? (i + 1) : i;
				
				int l1 = (j + 1 <= 9) ? (j + 1) : j;
				for (; k <= k1; k++) {
					int l = (j - 1 >= 0) ? (j - 1) : j;
					for (; l <= l1; l++) {
						if (!this.field[k][l].equals("~")) {
							check2 = 1;							
						}
					}
				
				}
			}			
		}
		if(check2 == 1) {
//			System.out.println("You placed it too close to another one");
			return 4;
		}
		
		int[][] tempArr;
		if (col == 5) {
			tempArr = this.carrier; 
		} else if (col == 4) {
			tempArr = this.battleship;
		} else if (col == 3) {
			if (this.submarine[0][0] == -2) {
				tempArr = this.submarine;
			} else {
				tempArr = this.cruiser;
			}			
		} else {
			tempArr = this.destroyer;
		}
				
		
		//Filling in the cells
		for (int i = num1S1, k = 0; i <= num1S2; i++) {
			for (int j = num2S1; j <= num2S2; j++) {
				this.field[i][j] = "O";
				tempArr[0][k] = i;
				tempArr[1][k] = j;
				k++;	
			}			
		}	
		
		return 0; //Ok!!!
	}
	
	public void printField () {
		
		System.out.println("  1 2 3 4 5 6 7 8 9 10");
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		for (int i = 0; i < this.field.length; i++) {
			System.out.print(abc[i] + " ");
			for (int j = 0; j < this.field[i].length; j++) {
				System.out.print(this.field[i][j] + " ");
			}
			System.out.println();
		}
				
	}
	
	public void printField (int flag) {
		
		System.out.println("  1 2 3 4 5 6 7 8 9 10");
		String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		for (int i = 0; i < this.field.length; i++) {
			System.out.print(abc[i] + " ");
			for (int j = 0; j < this.field[i].length; j++) {
				if (this.field[i][j].equals("O")) {
					System.out.print("~" + " ");
				} else {
					System.out.print(this.field[i][j] + " ");
				}
				
			}
			System.out.println();
		}
				
	}
}
