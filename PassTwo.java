import java.util.*;

public class PassTwo {
    static List<String> MNT = new ArrayList<>();
    static List<String> MDT = new ArrayList<>();
    static List<String> IC = new ArrayList<>();

    public static void main(String[] args) {
        // Simulated data from Pass 1
        MNT.add("DOUBLE\t0");
        MDT.add("LDA &ARG1");
        MDT.add("ADD &ARG1");
        MDT.add("STA &ARG1");
        MDT.add("MEND");

        IC.add("START 100");
        IC.add("DOUBLE X");
        IC.add("DOUBLE Y");
        IC.add("END");

        System.out.println("--- PASS-II OUTPUT ---");
        passTwo();
    }

    static void passTwo() {
        for (String line : IC) {
            String[] parts = line.split(" ");
            boolean expanded = false;

            for (String entry : MNT) {
                String[] m = entry.split("\t");
                String macroName = m[0];
                int ptr = Integer.parseInt(m[1]);

                if (parts[0].equals(macroName)) {
                    expanded = true;
                    String arg = parts[1];
                    for (int i = ptr; i < MDT.size(); i++) {
                        if (MDT.get(i).equals("MEND")) break;
                        System.out.println(MDT.get(i).replace("&ARG1", arg));
                    }
                }
            }
            if (!expanded && !line.equals("END")) System.out.println(line);
        }
    }
}
