/* Courtney Hunt JAVA 1B Assignment 6 Foothill College
 * 
 * This program adds/removes cards to/from the CardQueue class (an extension
 * of the Queue class) which creates nodes via the CardNode class (an extension
 * of the Node class). Sample output adds 5 valid Cards to the CardQueue, prints
 * the CardQueue to the console, then removes the 5 cards in a FIFO
 * (first-in-first-out) manner and displays them to the console as
 * removed. QueueEmptyException has been added and will display to console
 * when the Queue is empty. I also added my own CardEmptyException where the
 * original FloatStack code that CardQueue was based off of noted that we could
 * "throw an exception there if we knew how".
 */

public class Foothill
{
   public static char[] valueRanks = { '2', '3', '4', '5', '6'};

   public static void main (String[] args)
   {
      // CardQueue sample output
      CardQueue queue1 = new CardQueue();
      Card p1;

      // build the stack
      for (char rank : valueRanks)
      {
         p1 = new Card(rank, Card.Suit.hearts);
         try
         {
            queue1.addCard(p1);
         }
         catch(CardEmptyException ex)
         {
            System.out.println("Card is null.");
         }
      }
      System.out.println("Queue: " + queue1.toString());
      System.out.println();

      try
      {
         // show the stack, deleting as you pop
         while ((p1 = queue1.removeCard()) != null)
         {
            System.out.print("Removed: " + p1.toString());
            System.out.println();
         }
      }
      catch(QueueEmptyException ex)
      {
         System.out.println("Queue is empty.");
      }
   }
}

//Class Node  ----------------------------------
class Node
{
   private static final String DEFAULT_NODE = "(generic node)";
   protected Node next;
   protected String nodeString;

   // constructor
   public Node()
   {
      next = null;
      this.nodeString = DEFAULT_NODE;
   }

   // console display
   public String toString()
   {
      return this.nodeString;
   }
}

//Class Queue ---------------------------------------
class Queue
{
   // pointers to head node in stack and tail node in the stack
   private Node youngest, oldest;

   // constructor
   public Queue()
   {
      youngest = null;
      oldest = null;
   }

   public void add(Node newNode)
   {
      // Emergency return
      if (newNode == null)
         return;

      if (oldest == null)
         oldest = newNode;

      if (youngest != null)
         youngest.next = newNode;

      youngest = newNode;
   }  

   public Node remove()
         throws QueueEmptyException
   {
      if (oldest == null)
         throw new QueueEmptyException();

      Node temp = oldest;

      oldest = temp.next;
      temp.next = null;

      return temp;
   }

   // console display
   public String toString()
   {
      Node p;
      String queueString = "";
      // Display all the nodes in the stack
      for( p = oldest; p != null; p = p.next )
         queueString = queueString + p.toString() + " | ";

      return queueString;
   }
}

class QueueEmptyException extends Exception
{
}

class CardEmptyException extends Exception
{
}

//Class CardNode  ----------------------------------
class CardNode extends Node
{
   // additional data for subclass
   private Card card;

   // constructor
   public CardNode(Card card)
   {
      super();  // constructor call to base class
      this.card = card;
   }

   // accessor
   public Card getCard()
   {
      return card;
   }

   // overriding toString()
   public String toString()
   {
      return card.toString();
   }
}

//Class CardQueue  ----------------------------------
class CardQueue extends Queue
{
   public void addCard(Card card)
         throws CardEmptyException
   {
      // don't allow pushing of null
      if (card == null)
         throw new CardEmptyException();
      // create a new CardNode
      CardNode cardNode = new CardNode(card);

      // add the CardNode onto the queue (base class call)
      super.add(cardNode);
   }

   public Card removeCard()
         throws QueueEmptyException
   {
      return ((CardNode)remove()).getCard();
   }
}

//class Card  ----------------------------------------------------------------
class Card
{   
   // type and constants
   public enum State {deleted, active} // not bool because later we may expand
   public enum Suit { clubs, diamonds, hearts, spades }

   // for sort.  
   public static char[] valueRanks = { '2', '3', '4', '5', '6', '7', '8', '9',
         'T', 'J', 'Q', 'K', 'A', 'X'};
   static Suit[] suitRanks = {Suit.clubs, Suit.diamonds, Suit.hearts,
         Suit.spades};
   static int numValsInOrderingArray = 14;  // 'X' = Joker

   // private data
   private char value;
   private Suit suit;
   State state;
   boolean errorFlag;

   // 4 overloaded constructors
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   public Card(char value)
   {
      this(value, Suit.spades);
   }
   public Card()
   {
      this('A', Suit.spades);
   }
   // copy constructor
   public Card(Card card)
   {
      this(card.value, card.suit);
   }

   // mutators
   public boolean set(char value, Suit suit)
   {
      char upVal;            // for upcasing char

      // can't really have an error here
      this.suit = suit;  

      // convert to uppercase to simplify
      upVal = Character.toUpperCase(value);

      // check for validity
      if (
            upVal == 'A' || upVal == 'K'
            || upVal == 'Q' || upVal == 'J'
            || upVal == 'T' || upVal == 'X'
            || (upVal >= '2' && upVal <= '9')
            )
      {
         errorFlag = false;
         state = State.active;
         this.value = upVal;
      }
      else
      {
         errorFlag = true;
         return false;
      }

      return !errorFlag;
   }

   public void setState( State state)
   {
      this.state = state;
   }

   // accessors
   public char getVal()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public State getState()
   {
      return state;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   // stringizer
   public String toString()
   {
      String retVal;

      if (errorFlag)
         return "** illegal **";
      if (state == State.deleted)
         return "( deleted )";

      // else implied

      if (value != 'X')
      {
         // not a joker
         retVal =  String.valueOf(value);
         retVal += " of ";
         retVal += String.valueOf(suit);
      }
      else
      {
         // joker
         retVal = "joker";

         if (suit == Suit.clubs)
            retVal += " 1";
         else if (suit == Suit.diamonds)
            retVal += " 2";
         else if (suit == Suit.hearts)
            retVal += " 3";
         else if (suit == Suit.spades)
            retVal += " 4";
      }

      return retVal;
   }

   public boolean equals(Card card)
   {
      if (this.value != card.value)
         return false;
      if (this.suit != card.suit)
         return false;
      if (this.errorFlag != card.errorFlag)
         return false;
      if (this.state != card.state)
         return false;
      return true;
   }

   // sort member methods
   public int compareTo(Card other)
   {
      if (this.value == other.value)
         return ( getSuitRank(this.suit) - getSuitRank(other.suit) );

      return (
            getValueRank(this.value)
            - getValueRank(other.value)
            );
   }

   public static void setRankingOrder(
         char[] valueOrderArr, Suit[] suitOrdeArr,
         int numValsInOrderingArray )
   {
      int k;

      // expects valueOrderArr[] to contain only cards used per pack,
      // including jokers, needed to define order for the game environment

      if (numValsInOrderingArray < 0 || numValsInOrderingArray > 14)
         return;

      Card.numValsInOrderingArray = numValsInOrderingArray;

      for (k = 0; k < numValsInOrderingArray; k++)
         Card.valueRanks[k] = valueOrderArr[k];

      for (k = 0; k < 4; k++)
         Card.suitRanks[k] = suitOrdeArr[k];
   }

   public static int getSuitRank(Suit st)
   {
      int k;

      for (k = 0; k < 4; k++)
         if (suitRanks[k] == st)
            return k;

      // should not happen
      return 0;
   }

   public  static int getValueRank(char val)
   {
      int k;

      for (k = 0; k < numValsInOrderingArray; k++)
         if (valueRanks[k] == val)
            return k;

      // should not happen
      return 0;
   }

   public static void arraySort(Card[] array, int arraySize)
   {
      for (int k = 0; k < arraySize; k++)
         if (!floatLargestToTop(array, arraySize - 1 - k))
            return;
   }

   private static boolean floatLargestToTop(Card[] array, int top)
   {
      boolean changed = false;
      Card temp;

      for (int k = 0; k < top; k++)
         if (array[k].compareTo(array[k+1]) > 0)
         {
            temp = array[k];
            array[k] = array[k+1];
            array[k+1] = temp;
            changed = true;
         };
         return changed;
   }
}

/* Sample run------------------------------------
Queue: 2 of hearts | 3 of hearts | 4 of hearts | 5 of hearts | 6 of hearts | 

Removed: 2 of hearts
Removed: 3 of hearts
Removed: 4 of hearts
Removed: 5 of hearts
Removed: 6 of hearts
Queue is empty.
-----------------------------------------------*/
