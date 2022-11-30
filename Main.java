package ships;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void createFile(int [][]game,Point []tempPtr) {
		int i=0,j=0;
		//create the file:
				try {
				File shipsGame=new File("C:\\Users\\Lazer\\eclipse-workspace\\Ships\\mygame.txt");
				if(shipsGame.createNewFile())
					System.out.println("new file created: "+shipsGame.getName());
				else 
					System.out.println("file already exists");
				} catch(IOException e){
					System.out.println("An error occurred.");
				      e.printStackTrace();
				}	
				//adding content
					try {
						FileWriter myWriter = new FileWriter("mygame.txt");
						myWriter.write(1+" "+1+" ");
						myWriter.write(1+" "+2+" ");
						myWriter.write(1+" "+3+" ");
						myWriter.write(1+" "+4+" ");
						myWriter.write(1+" "+5+" ");
						myWriter.write(5+" "+10+" ");
						myWriter.write(6+" "+10+" ");
						myWriter.write(7+" "+10+" ");
						myWriter.write(5+" "+5+" ");
						myWriter.write(5+" "+6+" ");
						myWriter.close();
					} catch (IOException e) {
					      System.out.println("An error occurred.");
						e.printStackTrace();
					}
			//changing the map according to the ships:
			
					try {
					      File shipsGame = new File("C:\\Users\\Lazer\\eclipse-workspace\\Ships\\mygame.txt");
					      Scanner myReader = new Scanner(shipsGame);
					      while (myReader.hasNextInt()) {
					        int indexI = myReader.nextInt();
					        int indexJ = myReader.nextInt(); 
					        game[indexI][indexJ]++;
					        //allocate the pointers to an array
					        Point ptr1=new Point(indexI,indexJ);
					        //System.out.println(ptr1);
					        tempPtr[i++]=ptr1;
					      }
					      myReader.close();
					    } catch (FileNotFoundException e) {
					      System.out.println("An error occurred.");
					      e.printStackTrace();
					    }
			
	}
	public static void PointArr(Point []tempPtr,Point [][]ptrArr) {
		int i=0,j=0,k=0;
		ptrArr[k][j++]=tempPtr[i];
		for(i=1;i<tempPtr.length;i++) {
			if(tempPtr[i]==null)return ;
			if((tempPtr[i].getI()==tempPtr[i-1].getI()||tempPtr[i].getJ()==tempPtr[i-1].getJ()) &&
					((tempPtr[i].getI()-tempPtr[i-1].getI())+(tempPtr[i].getJ()-tempPtr[i-1].getJ())==1))
				ptrArr[k][j++]=tempPtr[i];
			else {
				//growSizeI(ptrArr);
				j=0;
				ptrArr[++k][j++]=tempPtr[i];
			}
		}
	}
	public static void changeSize(Point [][]ptrArr) {
		Point []temp=new Point[ptrArr[0].length];
		int count=0,k=0,countOfLines=0;
		for(int i=0;i<ptrArr.length;i++) {
			count=0;
			for(int j=0;j<ptrArr[i].length;j++) {
				if(ptrArr[i][0]==null)countOfLines++;
				if(ptrArr[i][j]!=null)
					count++;
			} 
			temp=new Point[count];
			k=0;
			for(int j=0;j<ptrArr[i].length;j++) {
				if(ptrArr[i][j]!=null)
					temp[k++]=ptrArr[i][j];
			}
			ptrArr[i]=temp;
		}	
	}
	
	public static int statusOfShips(int game[][],Point [][]ptrArr,int guessI,int guessJ) {
	int i,j,k,exit=0;
	for(i=0;i<ptrArr.length;i++) {
		for(j=0;j<ptrArr[i].length;j++) {
			if(ptrArr[i][j].getI()==guessI && ptrArr[i][j].getJ()==guessJ) {
				for(k=0;k<ptrArr[i].length;k++) {
					if(game[ptrArr[i][k].getI()][ptrArr[i][k].getJ()]==1) {
						exit = 1;
						break;
						}
				} //for k
				if(exit==0) {
					for(k=0;k<ptrArr[i].length;k++) {
						game[ptrArr[i][k].getI()][ptrArr[i][k].getJ()]=0;
					} //for k inside of if 
				} //if exit==0
			} //if == ptrArr ==i ,j
		} //for j
	}   // for i
		
		return exit;
	}
	
	public static int winner(Point [][]ptrArr,int game[][]) {
		int i,j,count=0;
		for(i=0;i<ptrArr.length;i++) {
			for(j=0;j<ptrArr[i].length;j++) {
				if(game[ptrArr[i][j].getI()][ptrArr[i][j].getJ()]==1)
					count++;
				}
			}
		return count;
	}
	public static  void printGame(int game[][]) {
		int i,j;
		for(i=0;i<game.length;i++) {
			for(j=0;j<game.length;j++) {
				if(i==0 || j==0 || i== game.length-1 || j== game.length-1)
					System.out.print('*');
				else if(game[i][j]==0)
					System.out.print(' ');
				else 
					System.out.print('$');
			}
		System.out.println('\n');
		}
	}
	public static String guess(int game[][],Point [][]ptrArr,int guessI,int guessJ) {
		if(game[guessI][guessJ]==0) {
			return "Empty";
		}
		else if(game[guessI][guessJ]==1) {
			game[guessI][guessJ]=2;
			if(statusOfShips(game,ptrArr,guessI,guessJ)==1)
				return "You've hit";
			else 
				return "Bull! The ship has sunken";
		}
		else if(game[guessI][guessJ]==2) {
			return "You've already hit that index";
		}
		return "unvalid action";
	}
	
public static void main(String [] args) {
			//create a file with the indexes of the ships:
			
			int game[][]=new int [12][12];
			int countTurn=0,i,j,guessI,guessJ;
			Point [][]ptrArr=new Point[5][10];
			//temp array to contain all of the points
			Point []tempPtr=new Point[20];
			
			createFile(game,tempPtr);
			PointArr(tempPtr,ptrArr);

			changeSize(ptrArr);
			//starting the loop for the input:
			Scanner s=new Scanner(System.in);
			printGame(game);
			System.out.println("Please enter your guesses");
			for(i=0;i<30;i++) {
				if(winner(ptrArr,game)==0) {
					System.out.println("Congratulaions! You've won!!");
					break;
				}
				guessI=s.nextInt();
				if(guessI==-999)
					break;
				guessJ=s.nextInt();
				System.out.println(guess(game,ptrArr,guessI,guessJ));
			} 
			if(i==30){
				System.out.println("Game over");
				if(winner(ptrArr,game)==0)
					System.out.println("Congratulaions! You've won!!");
				else {
					System.out.println("Sorry ,you've lost! Maybe next time you will have more luck!");
					System.out.println("You just had to catch another "+winner(ptrArr,game)+" places in order to win this time");
					} 
				} //if i==30
	}
}
