package my.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class HashLookup {

    public static void main(String[] args) {
        String s = args.length > 0 ? args[0] : "20.txt";
        //s = "schema.txt";
        try {
            for(Sets m: Sets.values()) {
                System.out.println("\n\n"+m.name());
                MySet ms = m.getMySet();
                HashLookup hlook = new HashLookup(ms, s);
                long time = (new Date()).getTime();
                if (ms.sort()) {
                    long timediff = (new Date()).getTime() - time;
                    System.out.println("Sort time: "+timediff);
                    hlook.checkSorted(ms);
                }
                time = (new Date()).getTime();
                if (ms.sort()) {
                    long timediff = (new Date()).getTime() - time;
                    System.out.println("Re-sort time: "+timediff);
                    hlook.checkSorted(ms);
                }
                System.out.println(ms.getAll().size()+ " in get all");
                //hlook.findAll(s);                
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public boolean checkSorted(MySet ms) {
        String lasts = "";
        for(String ts: ms.getAll()) {
            //System.out.println(ts);
            if (ts.compareTo(lasts) < 0) {
                System.out.println("*** Not sorted");
                return false;
            }
            lasts = ts;
        }
        System.out.println("Sorted");
        return true;
    }
    
    private MySet set;
    public HashLookup(MySet set, String fname) throws FileNotFoundException {
        int MAX = 0;
        int count = 0;
        this.set = set;
        try(Scanner s = new Scanner(new File(fname))) {
            while(s.hasNext()) {
                if (count++ >= MAX && MAX > 0) break;
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
        System.out.println("Find All time: "+timediff);
    }
    
    
    enum Sets {
        //Array(new MyArraySet()),
        BubbleArray(new MyBubbleArraySet()),
        HeapArray(new MyHeapArraySet()),
        QuickArray(new MyQuickArraySet()),
        //List(new MyListSet()),
        //Set(new MySetSet()),
        //TreeSet(new MyTreeSetSet()),
        //HashSet1(new MyHashSet(1)),
        //HashSet5(new MyHashSet(5)),
        //HashSet55(new MyHashSet(55)),
        //HashSet555(new MyHashSet(555)),
        //HashSet5555(new MyHashSet(5555)) 
        ;
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
        public List<String> getAll();
        public boolean sort();
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

        @Override
        public List<String> getAll() {
            List<String>alist = new ArrayList<>();
            alist.addAll(list);
            return alist;
        }

        @Override
        public boolean sort() {
            return false;
        }
    }
    
    static class MyTreeSetSet implements MySet {
        TreeSet<String> list = new TreeSet<>();

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

        @Override
        public List<String> getAll() {
            List<String>alist = new ArrayList<>();
            alist.addAll(list);
            return alist;
        }

        @Override
        public boolean sort() {
            return true;
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

        @Override
        public List<String> getAll() {
            List<String> alist = new ArrayList<>();
            for(String[] slist: strs) {
                if (slist == null) continue;
                for (String s: slist) {
                    if (s == null) break;
                    alist.add(s);
                }
            }
            return alist;
        }

        @Override
        public boolean sort() {
            return false;
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

        @Override
        public List<String> getAll() {
            return list;
        }

        @Override
        public boolean sort() {
            return false;
        }
    }

    static class MyArraySet implements MySet {
        int count = 0;
        String[] list = new String[60_000];
        
        @Override
        public void add(String s) {
            if (count + 1 < list.length) {
                list[count++] = s;
            }
        }

        @Override
        public boolean find(String s) {
            for(int i=0; i < count; i++) {
                if (list[i].equals(s)) { 
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int getSize() {
            return count;
        }

        @Override
        public List<String> getAll() {
            return Arrays.asList(Arrays.copyOf(list, count));
        }

        @Override
        public boolean sort() {
            Arrays.sort(list,0,count);
            return true;
        }
    }

    /*Verify slow performance*/
    static class MyBubbleArraySet extends MyArraySet {
        @Override
        public boolean sort() {
            for(int i=0; i<count; i++) {
                for(int j=i+1; j<count; j++) {
                    if (list[i].compareTo(list[j]) > 0) {
                        String t = list[i];
                        list[i] = list[j];
                        list[j] = t;
                    }
                }
            }
            return true;
        }
        
    }

    static class MyQuickArraySet extends MyArraySet {
        @Override
        public boolean sort() {
            quickSort(0, count-1);
            return true;
        }
        
        void quickSort(int lo, int hi) {
            if (lo >= hi) return;
            String pivot = list[lo];
            int pivotindex = lo;
            for(int i=lo+1; i<= hi; i++) {
                if (pivot.compareTo(list[i]) > 0) {
                    list[pivotindex] = list[i];
                    pivotindex++;
                    list[i] = list[pivotindex];
                    list[pivotindex] = pivot;
                }
            }
            quickSort(lo, pivotindex-1);
            quickSort(pivotindex+1,hi);
        }
    }

    static class MyHeapArraySet extends MyArraySet {
        @Override
        public boolean sort() {
            heapSort(0, count - 1);                
            return true;
        }
        
        void heapSort(int lo, int hi) {
            makeHeap(lo, hi);
            for(int i=hi; i> 0; i--) {
                swap(lo, i);
                siftDown(lo, i-1);
            }
        }
        
        void siftDown(int lo, int hi) {
            int bigger = -1;
            String cmp = list[lo];
            for(int child=lo*2+1; child <= lo*2+2 && child <= hi; child++) {
                if (cmp.compareTo(list[child]) < 0) {
                    cmp = list[child];
                    bigger = child;
                } 
            }
            if (bigger > 0) {
                swap(lo, bigger);
                siftDown(bigger, hi);
            }
        }
        
        void makeHeap(int lo, int hi) {
            for(int i=hi; i>=lo; i--) {
                siftDown(i, hi);
            }
        }
        
        int getParentIndex(int i) {
          return ((i + 1) / 2) - 1;  
        }
        
        void swap(int a, int b) {
            String temp = list[a];
            list[a] = list[b];
            list[b] = temp;
        }
    }

}
