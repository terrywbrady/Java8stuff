package my.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lambda {

    public static final String[] INIT = {"cc","aa1234", "aaaaa", "bb12", "bc123", "Ab1", "BB"};
    public static void main(String[] args) {
        Lambda lamb = new Lambda();
        //lamb.mapFuncs();
        try {
            lamb.readFile("schema.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortFuncs() {
        List<String> list = new ArrayList<>();
        for(String s: INIT) {
            list.add(s);
        }
        //list.sort((s1,s2) -> s1.compareToIgnoreCase(s2));
        //list.sort(Comparator.comparingInt(String::length));
        //list.sort((s1,s2) -> s1.compareToIgnoreCase(s2));
        //list.sort(Comparator.reverseOrder());
        list.sort((s1,s2) -> s1.length() == s2.length() ? s1.compareToIgnoreCase(s2) : Integer.compare(s1.length(), s2.length()));
        for(String s: list) {
            System.out.println(s);
        }
        
    }
    
    public void mapFuncs() {
        Map<String,Integer> map = new HashMap<>();
        for(String s: INIT) {
            map.merge(s.substring(0, 1).toLowerCase(), 1, Integer::sum);
        }
        for(String s: map.keySet()) {
            System.out.println(String.format("%s -> %d", s, map.get(s)));
        }
        
    }
    
    public void readFile(String file) throws FileNotFoundException, IOException {
        Map<String,Integer> map = new HashMap<>();
        try(Scanner scan = new Scanner(new File(file)).useDelimiter("[^a-zA-Z_]+")) {
            while(scan.hasNext()) {
                map.merge(scan.next(), 1, Integer::sum);
            }
            
        }
        map.keySet()
            .stream()
            .sorted((s1,s2) -> -1 * Integer.compare(map.get(s1), map.get(s2)))
            .limit(15)
            .forEach((s)-> System.out.println(String.format("%s -> %d", s, map.get(s))));
    }
}
