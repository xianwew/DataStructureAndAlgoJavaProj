import student.TestCase;
public class JunitTest extends student.TestCase {
JunitTest test;//field, make object im testing
public void setUp() {
test = new JunitTest();//constructor
}


public static void testInsert() {
SemManager semManager = new SemManager();
MyHashTable myHashTable = new MyHashTable(32);
int ID = 10;
String title = "Computational Biology and Bioinformatics in CS at Virginia Tech";
String dateTime = "0610071600";
int length = 60;
short x = 20;
short y = 10;
int cost = 30;
String desc = "";
String[] keywordList = {"Bioinformatics", "computation_biology", "Biology", "Computer_Science", "VT", "Virginia_Tech"};
Seminar seminar = new Seminar(ID, title, dateTime, length, x, y, cost, keywordList, desc);
myHashTable.insert(semManager, 10, seminar);
ID = 2;
Seminar seminar2 = new Seminar(ID, title, dateTime, length, x, y, cost, keywordList, desc);
myHashTable.insert(semManager, 2, seminar2);
//assertEquals(1.0, myHashTable.search(semManager, 2) ? 1.0: 0, 0.01);
//assertEquals(10.0, myHashTable.search(semManager, 10), 0.01);
assertTrue(myHashTable.search(semManager, 2));
assertTrue(myHashTable.search(semManager, 10));
}


//@JunitTest
//public void testDelete() {
//SemManager semManager = new SemManager();
//MyHashTable myHashTable = new MyHashTable(32);
//int ID = 10;
//String title = "Computational Biology and Bioinformatics in CS at Virginia Tech";
//String dateTime = "0610071600";
//int length = 60;
//short x = 20;
//short y = 10;
//int cost = 30;
//String desc = "";
//String[] keywordList = {"Bioinformatics", "computation_biology", "Biology", "Computer_Science", "VT", "Virginia_Tech"};	
//myHashTable.insert(semManager, 10, seminar);
//assertEquals(1.0, MyHashTable.search("key1"), 0.01);
//myHashTable.delete("key1");
//assertNull(MyHashTable.search("key1"));
//}
//
//
//@JunitTest
//// test methods must begin with the word "test"
//public void testSearch() {
////search
//MyHashTable.insert("key1", 1.0);
//MyHashTable.insert("key2", 10.0);
//int result1 = MyHashTable.search("key1");
//int result2 = MyHashTable.search("key2");
//assertEquals(1.0, result1, 0.01);
//assertEquals(2.0, result2, 0.01);
//}
//
//
//@JunitTest
//public void testprint() {
////print
//MyHashTable.insert("key1", 1.0);
//MyHashTable.insert("key2", 10.0);
//String printed= MyHashTable.print();
//assertTrue(printed.contains("key1: 1.0"));
//assertTrue(printed.contains("key2: 10.0"));
//}
}
