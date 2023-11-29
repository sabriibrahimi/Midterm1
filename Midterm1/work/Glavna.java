package Midterm1.work;


import java.util.Scanner;

//Within a company, a system is used to manage the tasks of employees. For each task, data is stored for: task id,
// duration - number of working hours (int hours) as well as priority (int priority). Priority is a value in the range
// [1, 10], where priority 10 indicates the highest priority.
//An employee has several assigned duties that are stored in two unilaterally connected lists. In the first list,
// the tasks that are not completed (To Do list) are stored, and in the second list, the tasks that he is
// currently working on (In Progress list) are stored.
//For each of the tasks, its importance can be calculated, as a double product of the number
// of working hours and the priority of the task.
//importance = 2*hours*priority
//Your task is to remove the most important task from the To Do list and put it at the top of the In Progress list.
//Then, the least important task should be removed from the In Progress list and put at the end of the To Do list.
public class Glavna {
    public static void work(SLL<Task> toDo, SLL<Task> inProgress) {
        SLLNode<Task> move1 = toDo.getFirst();
        SLLNode<Task> pom1 = null;
        SLLNode<Task> pom2 = null;
        int max = 0;

        while(move1 != null){
            int maxImportance = 2 * move1.element.getHours() * move1.element.getPriority();
            if(maxImportance > max) {
                max = maxImportance;
                pom1 = move1;
                pom2 = pom1;
            }
            move1 = move1.succ;
        }
        toDo.delete(pom1);
        inProgress.insertFirst(pom2.element);

        SLLNode<Task> move2 = inProgress.getFirst();
        SLLNode<Task> pom3 = null;
        SLLNode<Task> pom4 = null;
        int min = 9999;

        while(move2 != null){
            int minImportance = 0;
            minImportance = 2 * move2.element.getHours() * move2.element.getPriority();

            if(minImportance < min ){
                min = minImportance;
                pom3 = move2;
                pom4 = pom3;
            }
            move2 = move2.succ;
        }
        inProgress.delete(pom3);
        toDo.insertLast(pom4.element);

    }




    public static void main(String[] args)
    {
        SLL<Task> toDoList = new SLL<>();
        SLL<Task> InProgress = new SLL<>();

        Scanner input = new Scanner(System.in);
        int n=input.nextInt();
        int m=input.nextInt();
        for(int i=0;i<n;i++)
        {
            int id = input.nextInt();
            int hours = input.nextInt();
            int priority = input.nextInt();

            Task t1 = new Task(id,hours,priority);
            toDoList.insertLast(t1);
        }
        for(int j=0;j<m;j++)
        {
            int id = input.nextInt();
            int hours = input.nextInt();
            int priority = input.nextInt();

            Task t2 = new Task(id,hours,priority);
            InProgress.insertLast(t2);
        }
        work(toDoList,InProgress);
        System.out.println(toDoList.toString());
        System.out.println(InProgress.toString());
    }
}


class Task
{
    private int id;
    private int hours;
    private int priority;

    public Task(int id, int hours, int priority)
    {
        this.id = id;
        this.hours = hours;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public int getHours() {
        return hours;
    }

    public int getPriority() {
        return priority;
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