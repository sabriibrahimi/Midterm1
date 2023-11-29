package Midterm1.tarotCards;



import java.util.NoSuchElementException;
import java.util.Scanner;


//To perform a tarot reading, a fortuneteller uses a deck of cards, from which she takes exactly 12 cards and
// divides them into two halves, which are stored in two single-sided lists. Thus, the first list stores the
// data for the cards from the first part, while the second one stores the data for the cards from the second part.
//For each card, the data for: card id (int id) and card rank (int rank) are valid.
//Before starting the fortune-telling with the cards, the fortune-teller shuffles the cards, which is performed
// in 3 steps, always in this order:
//1. He takes the first card from the first part and puts it as the last card in the second part.
//2. After that, he takes the first card from the second part and puts it as the last card in the first part.
//3. Finally, he takes the penultimate card from the first part and places it in the middle of the second part.

public class Cards
{
    public static void tarotCards(SLL<Card> firstPart,SLL<Card> secondPart)
    {
        SLLNode<Card> first = firstPart.getFirst();
        SLLNode<Card> second = secondPart.getFirst();
        SLLNode<Card> pom3 = null;

        secondPart.insertLast(first.element);
        firstPart.delete(first);
        firstPart.insertLast(second.element);
        secondPart.delete(second);

        SLLNode<Card> third = firstPart.getFirst();
        while(third != null) {
            if(third.succ != null){
                pom3 = third;
            }
            third = third.succ;
        }

        SLLNode<Card> fourth = secondPart.getFirst();
        SLLNode<Card> pom4 = null;
        int size;
        if(secondPart.length() % 2 == 0){
            size = secondPart.length()/2;
        }else{
            size = secondPart.length()/2 + 1;
        }
        for(int i = 0;i <size;i++){
            fourth = fourth.succ;
        }
        secondPart.insertBefore(pom3.element, fourth);
        firstPart.delete(pom3);
    }
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        SLL<Card> prvDel = new SLL<>();
        SLL<Card> vtorDel = new SLL<>();

        for(int i=0;i<6;i++)
        {
            int id = input.nextInt();
            int rank = input.nextInt();
            Card c1 = new Card(id,rank);
            prvDel.insertLast(c1);
        }
        for(int j=0;j<6;j++)
        {
            int id = input.nextInt();
            int rank = input.nextInt();
            Card c2 = new Card(id,rank);
            vtorDel.insertLast(c2);
        }
        tarotCards(prvDel,vtorDel);
        System.out.println(prvDel.toString());
        System.out.println(vtorDel.toString());
    }
}

class Card
{
    private int id;
    private int rank;

    public Card(int id, int rank)
    {
        this.id=id;
        this.rank=rank;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

class SLLNode<E>
{
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class SLL<E> {
    private SLLNode<E> first;

    public SLL() {
        // Construct an empty SLL
        this.first = null;
    }

    public void deleteList() {
        first = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            SLLNode<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            SLLNode<E> tmp = first;
            ret += tmp + " ";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + " ";
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public void insertFirst(E o) {
        SLLNode<E> ins = new SLLNode<E>(o, first);
        first = ins;
    }

    public void insertAfter(E o, SLLNode<E> node) {
        if (node != null) {
            SLLNode<E> ins = new SLLNode<E>(o, node.succ);
            node.succ = ins;
        } else {
            System.out.println("Dadenot jazol e null");
        }
    }

    public void insertBefore(E o, SLLNode<E> before) {

        if (first != null) {
            SLLNode<E> tmp = first;
            if(first==before){
                this.insertFirst(o);
                return;
            }
            //ako first!=before
            while (tmp.succ != before)
                tmp = tmp.succ;
            if (tmp.succ == before) {
                SLLNode<E> ins = new SLLNode<E>(o, before);
                tmp.succ = ins;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
    }

    public void insertLast(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            SLLNode<E> ins = new SLLNode<E>(o, null);
            tmp.succ = ins;
        } else {
            insertFirst(o);
        }
    }

    public E deleteFirst() {
        if (first != null) {
            SLLNode<E> tmp = first;
            first = first.succ;
            return tmp.element;
        } else {
            System.out.println("Listata e prazna");
            return null;
        }
    }

    public E delete(SLLNode<E> node) {
        if (first != null) {
            SLLNode<E> tmp = first;
            if(first ==node){
                return this.deleteFirst();
            }
            while (tmp.succ != node && tmp.succ.succ != null)
                tmp = tmp.succ;
            if (tmp.succ == node) {
                tmp.succ = tmp.succ.succ;
                return node.element;
            } else {
                System.out.println("Elementot ne postoi vo listata");
                return null;
            }
        } else {
            System.out.println("Listata e prazna");
            return null;
        }

    }

    public SLLNode<E> getFirst() {
        return first;
    }

    public SLLNode<E> find(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.element != o && tmp.succ != null)
                tmp = tmp.succ;
            if (tmp.element == o) {
                return tmp;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
        return first;
    }
}
