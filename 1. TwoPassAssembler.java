import java.util.*;

class Symbol {
    String symbol;
    int address;

    Symbol(String symbol, int address) {
        this.symbol = symbol;
        this.address = address;
    }
}

public class TwoPassAssembler {
    static Map<String, String> MOT = new HashMap<>(); // Machine Opcode Table
    static Map<String, Integer> SYMTAB = new LinkedHashMap<>(); // Symbol Table
    static List<String> INTERMEDIATE = new ArrayList<>();

    public static void main(String[] args) {
        initializeMOT();
        String[] code = {
                "START 100",
                "READ A",
                "MOVER AREG, A",
                "ADD BREG, B",
                "MOVEM AREG, RESULT",
                "PRINT RESULT",
                "STOP",
                "A DC 5",
                "B DC 10",
                "RESULT DS 1",
                "END"
        };

        passOne(code);
        System.out.println("\n--- SYMBOL TABLE ---");
        SYMTAB.forEach((k, v) -> System.out.println(k + "\t" + v));

        System.out.println("\n--- INTERMEDIATE CODE ---");
        INTERMEDIATE.forEach(System.out::println);

        System.out.println("\n--- PASS-II OUTPUT ---");
        passTwo();
    }

    static void initializeMOT() {
        MOT.put("STOP", "00");
        MOT.put("ADD", "01");
        MOT.put("SUB", "02");
        MOT.put("MULT", "03");
        MOT.put("MOVER", "04");
        MOT.put("MOVEM", "05");
        MOT.put("COMP", "06");
        MOT.put("BC", "07");
        MOT.put("DIV", "08");
        MOT.put("READ", "09");
        MOT.put("PRINT", "10");
    }

    static void passOne(String[] code) {
        int LC = 0;

        for (String line : code) {
            String[] parts = line.split("[ ,]+");
            String opcode = parts[0];

            switch (opcode) {
                case "START":
                    LC = Integer.parseInt(parts[1]);
                    INTERMEDIATE.add("(AD,01)\t(C," + LC + ")");
                    break;

                case "END":
                    INTERMEDIATE.add("(AD,02)");
                    break;

                case "DC":
                    SYMTAB.put(parts[0], LC);
                    INTERMEDIATE.add("(DL,01)\t(C," + parts[1] + ")");
                    LC++;
                    break;

                case "DS":
                    SYMTAB.put(parts[0], LC);
                    INTERMEDIATE.add("(DL,02)\t(C," + parts[1] + ")");
                    LC += Integer.parseInt(parts[1]);
                    break;

                default:
                    if (MOT.containsKey(opcode)) {
                        String codeStr = "(IS," + MOT.get(opcode) + ")";
                        String operand1 = (parts.length > 1) ? parts[1] : "";
                        String operand2 = (parts.length > 2) ? parts[2] : "";

                        if (!operand1.equals("") && operand1.endsWith("REG"))
                            codeStr += "\t(" + operand1 + ")";
                        if (!operand2.equals("")) {
                            if (!SYMTAB.containsKey(operand2))
                                SYMTAB.put(operand2, -1);
                            codeStr += "\t(S," + operand2 + ")";
                        }

                        INTERMEDIATE.add(codeStr);
                        LC++;
                    } else if (!opcode.equals("START") && !opcode.equals("END")) {
                        // For undefined labels
                        SYMTAB.putIfAbsent(opcode, LC);
                    }
                    break;
            }
        }
    }

    static void passTwo() {
        for (String line : INTERMEDIATE) {
            if (line.contains("(IS")) {
                for (String sym : SYMTAB.keySet()) {
                    if (line.contains("(S," + sym + ")")) {
                        line = line.replace("(S," + sym + ")", String.valueOf(SYMTAB.get(sym)));
                    }
                }
                System.out.println(line);
            }
        }
    }
}
