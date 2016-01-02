/* I was wondering if you could use the virtual dispatch of Java to have
    every sub class of element implement a correct accept, but it's not like that
    */
public class main
{ public static void main (String arguments)
  { for (element e : new element [] {new fooelement (), new barelement ()})
    { e.visit (new visitor ());
    }
  }
}