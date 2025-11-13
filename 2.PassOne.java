import java.util.*;

public class PassOne {
    static List<String> MNT = new ArrayList<>();
    static List<String> MDT = new ArrayList<>();
    static List<String> IC = new ArrayList<>();

    public static void main(String[] args) {
        String[] code = {
            "MACRO",
            "DOUBLE &ARG1",
            "LDA &ARG1",
            "ADD &ARG1",
            "STA &ARG1",
            "MEND",
            "START 100",
            "DOUBLE X",
            "DOUBLE Y",
            "END"
        };

        passOne(code);

        System.out.println("\n--- MNT (Macro Name Table) ---");
        for (String s : MNT) System.out.println(s);

        System.out.println("\n--- MDT (Macro Definition Table) ---");
        for (String s : MDT) System.out.println(s);

        System.out.println("\n--- INTERMEDIATE CODE ---");
        for (String s : IC) System.out.println(s);
    }

    static void passOne(String[] code) {
        boolean inMacro = false;
        String macroName = "";
        int MDTptr = 0;

        for (int i = 0; i < code.length; i++) {
            String line = code[i].trim();
            String[] parts = line.split(" ");

            if (parts[0].equals("MACRO")) {
                inMacro = true;
                continue;
            }
            if (inMacro) {
                if (parts[0].equals("MEND")) {
                    MDT.add("MEND");
                    inMacro = false;
                    macroName = "";
                } else if (macroName.equals("")) {
                    macroName = parts[0];
                    MNT.add(macroName + "\t" + MDTptr);
                } else {
                    MDT.add(line);
                    MDTptr++;
                }
            } else {
                if (parts[0].equals("END")) {
                    IC.add("END");
                    break;
                } else {
                    IC.add(line);
                }
            }
        }
    }
}

