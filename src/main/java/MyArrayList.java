/**
 * Собственная реализация ArrayList.
 *
 * @param <T> Тип элементов в списке.
 */
public class MyArrayList<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    /**
     * Создает пустой список с начальной емкостью по умолчанию.
     */
    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param item Элемент для добавления.
     */
    public void add(T item) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = item;
    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index Индекс элемента.
     * @return Элемент по индексу.
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        return (T) elements[index];
    }

    /**
     * Устанавливает значение элемента по индексу.
     *
     * @param index Индекс элемента.
     * @param item  Новое значение элемента.
     */
    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        elements[index] = item;
    }

    /**
     * Возвращает размер списка.
     *
     * @return Размер списка.
     */
    public int size() {
        return size;
    }

    private void ensureCapacity() {
        int newCapacity = elements.length * 2;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, size);
        elements = newArray;
    }
}
