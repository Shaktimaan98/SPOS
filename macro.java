import java.io.*;
import java.util.*;

public class macro{

    public static void main( String[] args){
        try{
        BufferedReader br = new BufferedReader( new FileReader("input.txt"));
        PrintWriter mntFile = new PrintWriter("mnt.txt");
        PrintWriter mdtFile = new PrintWriter("mdt.txt");
        PrintWriter alaFile = new PrintWriter("ala.txt");

        int alaCount=0;
        String macroName="";
        String[] ALA= new String[10];
        String line;
        int mdtIndex=0;
        boolean inMacro = false;

        while( (line = br.readLine()) != null ){
            line = line.trim();
            if(line.equals("")) continue;
            StringTokenizer str= new StringTokenizer(line, " ,");

            if(line.equals("MACRO")){
                inMacro=true;
                line= br.readLine();
                StringTokenizer st2= new StringTokenizer(line, " ,");
                macroName= st2.nextToken();

                mntFile.println(macroName + "\t" + mdtIndex);

                alaCount=0;
                while(st2.hasMoreTokens()){
                    String arg= st2.nextToken();
                    if(arg.startsWith("&")){
                        ALA[alaCount]=arg;
                        alaFile.println(alaCount + "\t"+ ALA[alaCount]);
                        alaCount++;

                    }
                }
                alaFile.println();
                continue; 
            }

            if(inMacro){
                if(line.equals("MEND")){
                    inMacro=false;
                    mdtFile.println("MEND");
                    mdtIndex++;
                    continue;
                    
                }

                for(int i=0;i<alaCount;i++){
                    if(ALA[i]!= null && line.contains(ALA[i])){
                         line = line.replace(ALA[i], "#" + i);

                    }
                }
                mdtFile.println(line);
                mdtIndex++;
            }


                
            }
        
        br.close();
        mntFile.close();
        mdtFile.close();
        alaFile.close();

        System.out.println("Macro Pass-I completed successfully.");

        }
        catch(Exception e){
        System.out.println("Error:"+ e);

    }
    
}


}

    
