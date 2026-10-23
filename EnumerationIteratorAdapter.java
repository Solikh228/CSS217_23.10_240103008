import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class EnumerationIteratorAdapter<T> implements Iterator<T> {
    private final Enumeration<T> enumeration;
    
    public EnumerationIteratorAdapter(Enumeration<T> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public T next() {
        return enumeration.nextElement();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported");
    }

    public static void main(String[] args) {
        String studentId = "240103008";
        System.out.println("Student ID: " + studentId);

        Vector<String> vector = new Vector<>();
        vector.add(studentId);
        vector.add("Database Systems");
        vector.add("Software Engineering");
        vector.add("Computer Networks");

        Enumeration<String> legacyEnum = vector.elements();

        Iterator<String> iterator = new EnumerationIteratorAdapter<>(legacyEnum);

        while (iterator.hasNext()) {
            System.out.println("Item: " + iterator.next());
        }
    }
}