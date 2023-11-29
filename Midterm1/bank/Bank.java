package Midterm1.bank;


import java.util.NoSuchElementException;
import java.util.Scanner;

//Within a bank, a system is used that stores the data for the bank's customers. In that system, the data for
// one customer is given in the format: user ID, years of loyalty, number of active account transactions.
//Considering that the bank has been in existence for 50 years, no customer can have loyalty greater than 50.
//Within the system, users are stored in two unidirectional linked lists. The first list stores the data for
// ordinary users (Normal), while the second list stores the data for users with special privileges (Golden).
//For each client, its importance to the bank can be calculated according to the formula:
//Importance = Loyalty * 10 + Accounts * 20
//The bank decided that it wanted to make a change, that is, to remove the least important
// customer from the Golden list and put him at the end of the Normal list. Then, to remove the most
// important customer from the Normal list and put it at the end of the Golden list.

public class Bank
{
    public static void bank(SLL<Client> normal, SLL<Client> golden)
    {
        SLLNode<Client> gold = golden.getFirst();
        SLLNode<Client> pom1 = null;
        int min = 9999;
        while(gold != null) {
            int importanceMin = 0;
            importanceMin = gold.element.getLoyalty() * 10 + gold.element.getAccounts() * 20;
            if(importanceMin < min){
                min = importanceMin;
                pom1 = gold;
            }
            gold = gold.succ;
        }
        golden.delete(pom1);
        normal.insertLast(pom1.element);
        System.out.println("----GOLDEN LISTATA:-----");
        System.out.println(golden);
        System.out.println("----NORMAL LISTATA:-----");
        System.out.println(normal);

        SLLNode<Client> nrm = normal.getFirst();
        SLLNode<Client> pom2 = null;
        int max  = 0;
        while(nrm != null) {
            int maxImportance = 0;
            maxImportance = nrm.element.getLoyalty() * 10 + nrm.element.getAccounts() * 20;
            if(maxImportance > max){
                max = maxImportance;
                pom2 = nrm;
            }
            nrm = nrm.succ;
        }
        normal.delete(pom2);
        golden.insertLast(pom2.element);
        System.out.println("----NORMAL LISTATA:-----");
        System.out.println(normal);
        System.out.println("----GOLDEN LISTATA:-----");
        System.out.println(golden);
        System.out.println();
    }
    public static void main(String[] args)
    {
        SLL<Client> Normal = new SLL<>();
        SLL<Client> Golden = new SLL<>();

        Scanner vnesi = new Scanner(System.in);
        int n = vnesi.nextInt();
        int m = vnesi.nextInt();

        for(int i=0;i<n;i++)
        {
            int id = vnesi.nextInt();
            int loyalty = vnesi.nextInt();
            int accounts = vnesi.nextInt();
            Client c1 = new Client(id,loyalty,accounts);
            Normal.insertLast(c1);
        }
        for(int j=0;j<m;j++)
        {
            int id = vnesi.nextInt();
            int loyalty = vnesi.nextInt();
            int accounts = vnesi.nextInt();
            Client c2 = new Client(id,loyalty,accounts);
            Golden.insertLast(c2);
        }
        bank(Normal,Golden);
        System.out.println(Normal.toString());
        System.out.println(Golden.toString());
    }

}

class Client
{
    private int id;
    private int loyalty;
    private int accounts;

    public Client(int id, int loyalty, int accounts)
    {
        this.id = id;
        this.loyalty = loyalty;
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public int getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
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

class SLLNode<E> {
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