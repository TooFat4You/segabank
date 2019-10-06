package segabank.csv;

public interface ICSV<T> {
    String toCSV(T object);
}
