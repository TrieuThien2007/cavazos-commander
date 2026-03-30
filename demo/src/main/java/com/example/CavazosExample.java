package com.example;

import java.util.Random;
import java.util.Stack;
import java.util.Scanner;
import org.json.simple.*;

public class CavazosExample {

  public static void main(String[] args) {
    String fileName = "F:\\cavazos-commander\\demo\\src\\main\\resources\\commands.json";

    // read coammands
    JSONArray commandJSONArray = JSONFile.readArray(fileName);
    String[] commandArray = getCommandArray(commandJSONArray);
    Stack<String> history = new Stack<>();
    Stack<String> redoStack = new Stack<>();

    // print menu
    Scanner sc = new Scanner(System.in);
    char choice;
    do {
      printMenu();
      choice = sc.nextLine().charAt(0);

      switch (choice) {
        case 'i':
          issueCommand(commandArray, history, redoStack);
          break;

        case 'l':
          listCommands(commandArray);
          break;

        case 'u':
          undoCommand(history, redoStack);
          break;

        case 'r':
          redoCommand(history, redoStack);
          break;
        

        default:
          System.out.println("Invalid command!");
      }
    } while (choice != 'q');
    sc.close();
  }

  // randomly issue commands from General Cavazos
  public static void randomCommand(String[] commandArray, int numCommand) {
    Random rand = new Random();
    System.out.printf("Number\tCommand\n");
    System.out.printf("------\t---------------\n");
    for (int i = 0; i < numCommand; i++) {
      int randIndex = rand.nextInt(commandArray.length);
      System.out.printf("%04d\t%s\n", randIndex, commandArray[randIndex]);
    }
  }

  // print command array
  public static void print(String[] commandArray) {
    System.out.printf("Number\tCommand\n");
    System.out.printf("------\t---------------\n");
    for (int i = 0; i < commandArray.length; i++) {
      System.out.printf("%04d\t%s\n", i, commandArray[i]);
    }
  }

  // get array of commands
  public static String[] getCommandArray(JSONArray commandArray) {
    String[] arr = new String[commandArray.size()];

    // get names from json object
    for (int i = 0; i < commandArray.size(); i++) {
      String command = commandArray.get(i).toString();
      arr[i] = command;
    }
    return arr;
  }

  private static void printMenu() {
    System.out.println("------------------------------------------------------------");
    System.out.println("General Cavazos Commander App");
    System.out.println("------------------------------------------------------------");
    System.out.println("i\tIssue a command");
    System.out.println("l\tList all of the commands");
    System.out.println("u\tUndo the last command that was issued");
    System.out.println("r\tRedo the last command that was issued");
    System.out.println("q\tQuit");
    System.out.println("------------------------------------------------------------");
    System.out.print("Enter a command: ");
  }

  private static void issueCommand(String[] commandArray, Stack<String> history, Stack<String> redoStack) {
    Random rand = new Random();
    int randIndex = rand.nextInt(commandArray.length);

    String command = commandArray[randIndex];

    System.out.printf("%04d\t%s\n", randIndex, command);

    history.push(command);
    redoStack.clear();
  }

  private static void listCommands(String[] commandArray) {
    print(commandArray);
  }

  private static void undoCommand(Stack<String> history, Stack<String> redoStack) {
    if (history.isEmpty()) {
      System.out.println("No command to undo!");
    } else {
      String removed = history.pop();
      redoStack.push(removed);
      System.out.println("Undo: " + removed);
    }
  }

  private static void redoCommand(Stack<String> history, Stack<String> redoStack) {
    if (redoStack.isEmpty()) {
      System.out.println("No command to redo!");
    } else {
      String cmd = redoStack.pop();
      history.push(cmd);
      System.out.println("Redo: " + cmd);
    }
  }

}
