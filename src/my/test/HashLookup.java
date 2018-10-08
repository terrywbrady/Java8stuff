package my.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

public class HashLookup {

    public static void main(String[] args) {
        String s = args.length > 0 ? args[0] : "20.txt";
        try {
            for(Sets m: Sets.values()) {
                System.out.println(m.name());
                HashLookup hlook = new HashLookup(m.getMySet(), s);
                hlook.findAll(s);                
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private MySet set;
    public HashLookup(MySet set, String fname) throws FileNotFoundException {
        this.set = set;
        try(Scanner s = new Scanner(new File(fname))) {
            while(s.hasNext()) {
                String str = s.next();
                if (!set.find(str)) set.add(str);
            }
        }
    }
    
    public void findAll(String fname) throws FileNotFoundException {
        System.out.println(set.getSize() + " items");
        long time = (new Date()).getTime();
        try(Scanner s = new Scanner(new File(fname))) {
            int count = 0;
            while(s.hasNext()) {
                if (set.find(s.next())) count++;
            }
            System.out.println(count + " found");
        }
        long timediff = (new Date()).getTime() - time;
        System.out.println(timediff);
    }
    
    
    enum Sets {
        List(new MyListSet()),
        Set(new MySetSet()),
        HashSet1(new MyHashSet(1)),
        HashSet5(new MyHashSet(5)),
        HashSet55(new MyHashSet(55)),
        HashSet555(new MyHashSet(555)),
        HashSet5555(new MyHashSet(5555)) ;
        private MySet m;
        Sets(MySet m) {
            this.m = m;
        }
        public MySet getMySet() {return m;}
        
    }
        
    static interface MySet {
        public void add(String s);
        public boolean find(String s);
        public int getSize();
    }
    
    static class MySetSet implements MySet {
        HashSet<String> list = new HashSet<>();

        @Override
        public void add(String s) {
            list.add(s);
        }

        @Override
        public boolean find(String s) {
            return list.contains(s);
        }

        @Override
        public int getSize() {
            return list.size();
        }
    }
    
    static class MyHashSet implements MySet {
        int count = 0;
        String[][] strs;
        MyHashSet(int size) {
            strs = new String[size][];            
        }
        
        int getHash(String s) {
            return Math.abs(s.hashCode()) % strs.length;
        }

        @Override
        public void add(String s) {
            int i = getHash(s);
            String[] sarr = strs[i];
            if (sarr == null) {
                sarr = new String[10];
                strs[i] = sarr;
            }
            int ai = 0;
            for(; ai < sarr.length && sarr[ai]!=null; ai++) {}
            if (ai < sarr.length) {
                sarr[ai] = s;
                count++;
                return;
            }
            String[] narr = new String[sarr.length * 2];
            for(ai =0; ai<sarr.length; ai++) {
                narr[ai] = sarr[ai];
            }
            narr[ai] = s;
            strs[i] = narr;
            count++;
        }

        @Override
        public boolean find(String s) {
            int i = getHash(s);
            String[] sarr = strs[i];
            if (sarr == null) return false;
            int ai = 0;
            for(; ai < sarr.length && sarr[ai]!=null; ai++) {
                if (sarr[ai].equals(s)) return true;
            }
            return false;
        }

        @Override
        public int getSize() {
            return count;
        }
        
    }
    
    static class MyListSet implements MySet {
        ArrayList<String> list = new ArrayList<>();
        
        @Override
        public void add(String s) {
            list.add(s);
        }

        @Override
        public boolean find(String s) {
            return list.contains(s);
        }
        
        @Override
        public int getSize() {
            return list.size();
        }
    }
 
}
