import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        // Создаем MyArrayList и добавляем элементы
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(1);
        list.add(5);

        // Выводим несортированный список
        System.out.println("Несортированный список:");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();

        // Сортируем список с использованием QuickSort
        QuickSort.quickSort(list, Comparator.naturalOrder());

        // Выводим отсортированный список
        System.out.println("Отсортированный список:");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
}
