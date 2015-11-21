import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
/**
 * Created by Ayush on 19/11/2015.
 */
public class spellchecker {
    static final HashMap<String, Integer> nWords = new HashMap<String, Integer>();
    public static void train(String file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        Pattern p = Pattern.compile("\\w+");
        for(String temp = ""; temp != null; temp = in.readLine()){
            Matcher m = p.matcher(temp.toLowerCase());
            while(m.find()){
                nWords.put((temp = m.group()), nWords.containsKey(temp) ? nWords.get(temp) + 1 : 1);
            }
        }
        in.close();
    }
    public static ArrayList<String> editDist1(String word){
        ArrayList<String> ret = new ArrayList<>();
        for(int i=0;i<word.length();i++){
            ret.add(word.substring(0,i)+word.substring(i+1));
        }

        for(int i=0;i<word.length()-1;i++){
            ret.add(word.substring(0,i)+word.substring(i).charAt(1)+word.substring(i).charAt(0)+word.substring(i+2));
        }

        for(int i=0;i<word.length();i++){
            for(char j ='a';j<='z';j++){
                ret.add(word.substring(0,i)+j+word.substring(i+1));
            }
        };

        for(int i=0;i<word.length();i++){
            for(char j ='a';j<='z';j++){
                ret.add(word.substring(0,i)+j+word.substring(i));
            }
        };

        return ret;
    }
    public static String possibleCorrection(String word){
        ArrayList<String> edt1 = editDist1(word);
        HashMap<Integer,String> possibleCorrections = new HashMap<>();

        for(String edt: edt1){
            if(nWords.containsKey(edt)){
                possibleCorrections.put(nWords.get(edt),edt);
            }
        }

        if(!possibleCorrections.isEmpty()){
            return possibleCorrections.get(Collections.max(possibleCorrections.keySet()));
        }

        for(String edt: edt1){
            ArrayList<String> edt2 = editDist1(edt);
            for(String edtword2: edt2)
              if(nWords.containsKey(edtword2)){
                possibleCorrections.put(nWords.get(edtword2),edtword2);
              }
        }

        if(!possibleCorrections.isEmpty()){
            return possibleCorrections.get(Collections.max(possibleCorrections.keySet()));
        }

        return "Sorry no suggestion";
    }
    public static void main(String args[]) throws IOException {
        train("D:\\my_folder\\hacknight\\algo\\src\\TrainSpellCheck.txt");
        while(true){
        Scanner sc = new Scanner(System.in);
        String mispelled = sc.nextLine();
        System.out.println(possibleCorrection(mispelled));
        }
    }
    }


