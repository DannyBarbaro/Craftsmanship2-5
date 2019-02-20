package parser.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Parser {

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    System.out.println("Welcome to the expression parser.\n" +
    "Please input your expression or type 'exit' to quit\n" +
    "All variables and operators must be separated with a single space.\n" +
    "Press Enter when you have finished your expression");
    do {
      String userInput = inputScanner.nextLine();
      if (userInput.equals("exit")) {
        break;
      }
      List<Token> expression = new ArrayList<>();
      for (String part : userInput.split(" ")) {
        expression.add(tokenRepresentation(part));
      }
      Optional<Node> r = NonTerminalSymbol.parseInput(expression);
      printResult(r);
    } while (true);
    inputScanner.close();
  }

  private static Map<String, TerminalSymbol> stringToConnectorMap = new HashMap<String, TerminalSymbol>() {
    {
      put("+", TerminalSymbol.PLUS);
      put("-", TerminalSymbol.MINUS);
      put("*", TerminalSymbol.TIMES);
      put("/", TerminalSymbol.DIVIDE);
      put("(", TerminalSymbol.OPEN);
      put(")", TerminalSymbol.CLOSE);
    }
  };

  private static Token tokenRepresentation(String representation) {
    if (stringToConnectorMap.containsKey(representation)) {
      return Connector.build(stringToConnectorMap.get(representation));
    } else {
      return Variable.build(representation);
    }
  }

  private static void printResult(Optional<Node> result) {
    if(result.isPresent()) {
      System.out.println(result.get());
    } else {
      System.out.println("Invalid expression");
    }
    System.out.println("Input next expression:");
  }
}