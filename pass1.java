import java.io.*;
import java.util.*;

public class pass1 {
    public static void main(String[] args) {
        int address = 0;
        String[] IS = {"MOVER", "MOVEM", "ADD", "SUB", "MUL"};
        String[] AD = {"START", "END"};
        String[] REG = {"AREG", "BREG", "CREG", "DREG"};
        String[] DL = {"DS", "DC"};

        String[] symbols = new String[20];
        int[] symAddr = new int[20];
        int symCount = 0;

        String[] literals = new String[20];
        int[] litAddr = new int[20];
        int litCount = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("initial.txt"));
            PrintWriter im = new PrintWriter("IM.txt");
            PrintWriter st = new PrintWriter("ST.txt");
            PrintWriter lt = new PrintWriter("LT.txt");

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("[ ,]+");
                String op = parts[0].toUpperCase();

                // START
                if (op.equals("START")) {
                    if (parts.length > 1)
                        address = Integer.parseInt(parts[1]);
                    im.println("(AD,01) " + address);
                }

                // END
                else if (op.equals("END")) {
                    im.println("(AD,02)");
                    for (int i = 0; i < litCount; i++) {
                        if (litAddr[i] == 0) {
                            litAddr[i] = address++;
                        }
                    }
                }

                // Declarative Statements (DS/DC)
                else if (Arrays.asList(DL).contains(parts[1].toUpperCase())) {
                    symbols[symCount] = parts[0];
                    symAddr[symCount] = address;
                    symCount++;
                    im.println("(DL," + (Arrays.asList(DL).indexOf(parts[1].toUpperCase()) + 1) + ") " + parts[2]);
                    address++;
                }

                // Imperative Statements
                else if (Arrays.asList(IS).contains(op)) {
                    im.print("(IS," + (Arrays.asList(IS).indexOf(op) + 1) + ") ");
                    for (int i = 1; i < parts.length; i++) {
                        String token = parts[i].toUpperCase();

                        if (Arrays.asList(REG).contains(token))
                            im.print("(REG," + token + ") ");
                        else if (token.startsWith("=")) {
                            literals[litCount] = token;
                            litAddr[litCount] = 0;
                            im.print("(L," + litCount + ") ");
                            litCount++;
                        } else {
                            symbols[symCount] = token;
                            symAddr[symCount] = address;
                            im.print("(S," + symCount + ") ");
                            symCount++;
                        }
                    }
                    im.println();
                    address++;
                }
            }

            // Write Symbol Table
            st.println("Index\tSymbol\tAddress");
            for (int i = 0; i < symCount; i++)
                st.println(i + "\t" + symbols[i] + "\t" + symAddr[i]);

            // Write Literal Table
            lt.println("Index\tLiteral\tAddress");
            for (int i = 0; i < litCount; i++)
                lt.println(i + "\t" + literals[i] + "\t" + litAddr[i]);

            br.close();
            im.close();
            st.close();
            lt.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
