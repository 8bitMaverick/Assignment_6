import java.util.*;
import java.text.*;
import javax.swing.*;
import java.lang.*;

public class Foothill
{
   public static void main (String[] args)
   {
      /*
      // Stack sample output
      Stack stk = new Stack();
      StackNode p;

      // build the stack
      for (int k = 0; k < 5; k++)
      {
         p = new StackNode();
         stk.push(p);
      }

      // show the stack, deleting as you pop
      while ( (p = stk.pop()) != null)
         p.show();
      System.out.println();

      // FloatNode sample output
      FloatNode flt1 = new FloatNode(3.33F), 
            flt2 = new FloatNode(4.44F), flt3 = new FloatNode(5.55F), q;

      // link up our three FloatNodes
      flt1.next = flt2;
      flt2.next = flt3; 

      // show the stack
      for ( q = flt1; q != null; q = (FloatNode)(q.next) )
      {
         q.show();
      }
      System.out.println();

      // FloatStack sample output
      FloatStack fstk = new FloatStack();
      float f;

      fstk.pushFloat(1.1F);
      fstk.pushFloat(2.2F);
      fstk.pushFloat(3.3F);
      fstk.pushFloat(4.4F);

      for (int k = 0; k < 5; k++)
         if ( (f = fstk.popFloat()) != FloatStack.STACK_EMPTY)
            System.out.print( f + " ");
         else
            System.out.print("(empty stack) ");
      System.out.println();
      */
      // Queue sample output
      Queue stk1 = new Queue();
      Node p1;

      // build the stack
      for (int k = 0; k < 5; k++)
      {
         p1 = new Node();
         stk1.add(p1);
         System.out.print(p1.toString());
      }
      System.out.println();
      
      try
      {
      // show the stack, deleting as you pop
      while ( (p1 = stk1.remove()) != null)
      {
         System.out.print("oldest: " + p1.toString());
         System.out.println();
      }
      }
      catch(NullPointerException ex)
      {
         System.out.println("Queue is empty.");
         // put contingency value into result
      }
   }
}

//Class Queue ---------------------------------------
class Queue
{
   // pointers to first node in stack and last node in the stack
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
   {
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
      for( p = youngest; p != null; p = p.next )
         queueString = queueString + p.toString();
      
      return queueString;
   }
}

//Class Node  ----------------------------------
class Node
{
   // data (we allow Stack class public access)
   protected Node next;
   protected String nodeString;
   
   // constructor
   public Node()
   {
      next = null;
      this.nodeString = "(generic node) ";
   }

   // console display
   public String toString()
   {
      return this.nodeString;
   }
}


class QueueEmptyException extends Exception
{
}

//Class CardQueue  ----------------------------------
class CardQueue extends Queue
{
 public void addCard(Card card)
       throws QueueEmptyException
 {
    // don't allow pushing of Float.MIN_VALUE 
    if (card == null)
       return;    // could throw an exception when we learn how
    // create a new FloatNode
    CardNode cardNode = new CardNode(card);

    // add the CardNode onto the queue (base class call)
    super.add(cardNode);
 }

 public Card removeCard()
       throws QueueEmptyException
 {
    CardNode card = (CardNode)remove();
    // remove a card from Queue
    if (card == null)
       throw new QueueEmptyException();
    return card.getCard();
 }
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
    String result = super.toString();
    return result;
 }
}



//Class Stack ---------------------------------------
class Stack
{
   // pointer to first node in stack
   private StackNode top;

   // constructor
   public Stack()
   {
      top = null;
   }

   public void push(StackNode newNode)
   {   
      if (newNode == null) 
         return;   // emergency return
      newNode.next = top;
      top = newNode;
   }  

   public StackNode pop()
   {
      StackNode temp;

      temp = top;
      if (top != null)
      {
         top = top.next; 
         temp.next = null; // don't give client access to stack!
      }
      return temp;      
   }

   // console display
   public void showStack()
   {
      StackNode p;

      // Display all the nodes in the stack
      for( p = top; p != null; p = p.next )
         p.show();
   }
}

//Class StackNode  ----------------------------------
class StackNode
{
   // data (we allow Stack class public access)
   protected StackNode next;

   // constructor
   public StackNode()
   {
      next = null;
   }

   // console display
   public void show()
   {
      System.out.print( "(generic node) ");
   }
}

//Class FloatStack  ----------------------------------
class FloatStack extends Stack
{
   public static final float STACK_EMPTY = Float.MIN_VALUE;

   public void pushFloat(float x)
   {
      // don't allow pushing of Float.MIN_VALUE 
      if (x == Float.MIN_VALUE)
         return;    // could throw an exception when we learn how
      // create a new FloatNode
      FloatNode fp = new FloatNode(x);

      // push the StackNode onto the stack (base class call)
      super.push(fp);
   }

   public float popFloat()
   {
      // pop a node
      FloatNode fp = (FloatNode)pop();
      if (fp == null)
         return STACK_EMPTY;
      else
         return fp.getData();
   }
}

//Class FloatNode  ----------------------------------
class FloatNode extends StackNode
{
   // additional data for subclass
   private float data;

   // constructor
   public FloatNode(float x)
   {
      super();  // constructor call to base class
      data = x;
   }

   // accessor
   public float getData()
   {
      return data;
   }

   // overriding show()
   public void show()
   {
      NumberFormat tidy = NumberFormat.getInstance(Locale.US);
      tidy.setMaximumFractionDigits(4);

      System.out.print("[" + tidy.format(data) + "] ");
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



//class Hand  ----------------------------------------------------------------
class Hand
{
   public static final int MAX_CARDS_PER_HAND = 100;  // should cover any game

   private Card[] myCards;
   private int numCards;

   //constructor
   public Hand()
   {
      // careful - we are only allocating the references
      myCards = new Card[MAX_CARDS_PER_HAND];
      resetHand();
   }

   // mutators
   public void resetHand() { numCards = 0; }

   public boolean takeCard(Card card)
   {
      if (numCards >= MAX_CARDS_PER_HAND)
         return false;

      // be frugal - only allocate when needed
      if (myCards[numCards] == null)
         myCards[numCards] = new Card();

      // don't just assign:  mutator assures active/undeleted      
      myCards[numCards++].set( card.getVal(), card.getSuit() );
      return true;
   }

   public Card playCard()
   {
      // always play  highest card in array.  client will prepare this position.
      // in rare case that client tries to play from a spent hand, return error

      Card errorReturn = new Card('E', Card.Suit.spades); // in rare cases

      if (numCards == 0)
         return errorReturn;
      else
         return myCards[--numCards];
   }

   public Card playCard(int cardIndex)
   {
      if ( numCards == 0 ) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.spades);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];

      numCards--;
      for(int i = cardIndex; i < numCards; i++)
      {
         myCards[i] = myCards[i+1];
      }

      myCards[numCards] = null;

      return card;
   }

   // accessors
   public String toString()
   {
      int k;
      String retVal = "Hand =  ( ";

      for (k = 0; k < numCards; k++)
      {
         retVal += myCards[k].toString();
         if (k < numCards - 1)
            retVal += ", ";
      }
      retVal += " )";
      return retVal;
   }

   int getNumCards()
   {
      return numCards;
   }

   Card inspectCard(int k)
   {
      // return copy of card at position k.
      // if client tries to access out-of-bounds card, return error

      Card errorReturn = new Card('E', Card.Suit.spades); // in rare cases

      if (k < 0 || k >= numCards)
         return errorReturn;
      else
         return myCards[k];
   }

   void sort()
   {
      // assumes that Card class has been sent ordering (if default not correct)
      Card.arraySort(myCards, numCards);
   }
}



//class Deck  ----------------------------------------------------------------
class Deck
{
   // six full decks (with jokers) is enough for about any game
   private static final int MAX_CARDS_PER_DECK = 6 * 54;
   private static Card[] masterPack;   // one 52-Card master to use for
   // initializing decks
   private Card[] cards;
   private int topCard;
   private int numPacks;

   private static boolean firstTime = true;  // avoid calling allcMstrPck > once

   public Deck()
   {
      this(1);
   }

   public Deck(int numPacks)
   {
      allocateMasterPack();  // do not call from init()
      cards = new Card[MAX_CARDS_PER_DECK];
      init(numPacks);
   }

   static private void allocateMasterPack()
   {
      int j, k;
      Card.Suit st;
      char val;

      // we're in static method; only need once / program: good for whole class
      if ( !firstTime )
         return;
      firstTime = false;

      // allocate
      masterPack = new Card[52];
      for (k = 0; k < 52; k++)
         masterPack[k] = new Card();

      // next set data
      for (k = 0; k < 4; k++)
      {
         // set the suit for this loop pass
         st = Card.Suit.values()[k];

         // now set all the values for this suit
         masterPack[13*k].set('A', st);
         for (val='2', j = 1; val<='9'; val++, j++)
            masterPack[13*k + j].set(val, st);
         masterPack[13*k+9].set('T', st);
         masterPack[13*k+10].set('J', st);
         masterPack[13*k+11].set('Q', st);
         masterPack[13*k+12].set('K', st);
      }
   }

   // set deck from 1 to 6 packs, perfecly ordered
   public void init(int numPacks)
   {
      int k, pack;

      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;

      // hand over the masterPack cards to our deck
      for (pack = 0; pack < numPacks; pack++)
         for (k = 0; k < 52; k++)
            cards[pack*52 + k] = masterPack[k];

      // this was slightly sloppy:  multiple packs point to same master cards
      // if something modified a card, we would be in trouble.  fortunately,
      // we don't expect a card to ever be modified after instantiated
      // in the context of a deck.

      this.numPacks = numPacks;
      topCard = numPacks * 52;
   }

   public void init()
   {
      init(1);
   }

   public int getNumCards()
   {
      return topCard;
   }

   public void shuffle()
   {
      Card tempCard;
      int k, randInt;

      // topCard is size of deck
      for (k = 0; k < topCard; k++)
      {
         randInt = (int)(Math.random() * topCard);

         // swap cards k and randInt (sometimes k == randInt:  okay)
         tempCard = cards[k];
         cards[k] = cards[randInt];
         cards[randInt] = tempCard;
      }
   }

   public Card takeACard()
   {
      return new Card();
   }

   public Card dealCard()
   {
      // always deal the topCard.  
      Card errorReturn = new Card('E', Card.Suit.spades); //  in rare cases

      if (topCard == 0)
         return errorReturn;
      else
         return cards[--topCard];
   }

   public boolean removeCard(Card card)
   {
      int k;
      boolean foundAtLeastOne;

      foundAtLeastOne = false;
      for (k = 0; k < topCard; k++)
      {
         // care: use while, not if, in case we copy to-be-removed from top to k
         while ( cards[k].equals(card) )
         {
            // overwrite card[k] with top of deck, then decrement topCard
            cards[k] = cards[topCard - 1];
            topCard--;
            foundAtLeastOne = true;
            // test because "while" causes topCard to decrease, possibly below k
            if ( k >= topCard )
               break;
         }
      }
      // did above work if k == topCard-1?  think about it
      return foundAtLeastOne;
   }

   public boolean addCard(Card card)
   {
      // don't allow too many copies of this card in the deck
      if (numOccurrences(card) >= numPacks)
         return false;

      cards[topCard++] = card;
      return true;
   }

   public Card inspectCard(int k)
   {
      // return copy of card at position k.
      // if client tries to access out-of-bounds card, return error

      Card errorReturn = new Card('E', Card.Suit.spades); //  in rare cases

      if (k < 0 || k >= topCard)
         return errorReturn;
      else
         return cards[k];
   }

   public int numOccurrences(Card card)
   {
      int retVal, k;

      retVal = 0;

      // assumption:  card is a default item:  not deleted and state=active)
      for (k = 0; k < topCard; k++)
      {
         if (inspectCard(k).equals(card))
            retVal++;
      }
      return retVal;
   }

   public String toString()
   {
      int k;
      String retString = "\n";

      for (k = 0; k < topCard; k++)
      {
         retString += cards[k].toString();
         if (k < topCard - 1)
            retString += " / ";
      }
      retString += "\n";

      return retString;
   }

   void sort()
   {
      // assumes that Card class has been sent ordering (if default not correct)
      Card.arraySort(cards, topCard);
   }
}
