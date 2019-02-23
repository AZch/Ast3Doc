import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    /**
     * Массив элементов
     */
    public static ArrayList<Elem> elems = new ArrayList<>();

    /**
     * Получить сартовый индекс из матрицы и записать его в объект Elem от которого будут происходить вычисления сетевого графа (первая вершина из которой выходят все дуги)
     * @param matrix исходный массив вершин ввиде матрицы
     * @return элемент класса @Elem, который содержит индекс элемента который является начальным и все остальные параметры равны нулю,
     *      если такого не обнаружено, то возвращается также элемент, но стартовый индекс равен -1 (все остальные параметры нулевые) (индекс не может равняться отрицательному числу)
     */
    public static Elem getIndexStart(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean dont = false;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][0] == matrix[j][1]) {
                    dont = true;
                    break;
                }
            }
            if (!dont)
                return new Elem(matrix[i][0], 0, 0, 0);
        }
        return new Elem(-1, 0, 0, 0);
    }

    /**
     * Получить конечный индекс из матрицы и записать его в объект Elem до которого будут происходить вычисления сетевого графа (первая вершина в которую выходят все дуги)
     * @param matrix исходный массив вершин ввиде матрицы
     * @return элемент класса @Elem, который содержит индекс элемента который является конечным и все остальные параметры равны нулю,
     *      если такого не обнаружено, то возвращается также элемент, но конечный индекс равен 0 (все остальные параметры нулевые) (индекс не может равняться отрицательному числу)
     */
    public static Elem getIndexEnd(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean dont = false;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][1] == matrix[j][0]) {
                    dont = true;
                    break;
                }
            }
            if (!dont)
                return new Elem(matrix[i][1], 0, 0, 0);
        }
        return new Elem(0, 0, 0, 0);
    }

    /**
     * Пререприсвоить pTi если он уже меньше чем size
     * @param index индекс элемента для которого нужно проверить и если что переприсвоить pTi
     * @param size -  pTi + tao от элемента по переданному индексу
     * @return полученный pTi для элемента с индексом index (при этом может быть не переприсвоен), если элемент не будет найден, то вернется -1
     */
    public static int checkNewpTi(int index, int size) {
        for (Elem elem : elems)
            if (elem.i == index) {
                if (elem.pTi < size)
                    elem.pTi = size;
                return elem.pTi;
            }

        return -1;
    }

    /**
     * Пререприсвоить nTi если он уже меньше чем size
     * @param index индекс элемента для которого нужно проверить и если что переприсвоить nTi
     * @param size - nTi - tao от элемента по переданному индексу
     * @return полученный nTi для элемента с индексом index (при этом может быть не переприсвоен), если элемент не будет найден, то вернется -1
     */
    public static int checkNewnTi(int index, int size) {
        for (Elem elem : elems)
            if (elem.i == index) {
                if (elem.nTi > size || elem.nTi == 0)
                    elem.nTi = size;
                return elem.nTi;
            }

        return -1;
    }

    /**
     * Получить элемент по индексу
     * @param i индекс, который необходимо найти среди элементов
     * @return объект класса @Elem, если такой элемент есть в массиве элементов, если такого элемента нет, то вернет null
     */
    public static Elem getElem(int i) {
        for (Elem elem : elems)
            if (elem.i == i) {
                return elem;
            }
        return null;
    }

    /**
     * Пройтись по всему графу от вершины с начальным индексом (indexStart) до вершины с конечным индексом (indexEnd)
     *      и пересчитать (получить) pTi элемент графа
     * @param matrix исходная матрица со всем вершинами и переходами, а также параметром tao
     * @param indexStart индекс стартовой першины от которой будет происходить обход
     * @param indexEnd индекс конечной вершины до которой будет происходить обход
     * @param size pTi для возможного пересчета для следующей вершины pTi
     */
    public static void findAllpTi(int[][] matrix, int indexStart, int indexEnd, int size) {
        for (int i = 0; i < matrix.length; i++) {
            if (indexStart == matrix[i][0]) {
                findAllpTi(matrix, matrix[i][1], indexEnd, checkNewpTi(matrix[i][1], getElem(matrix[i][0]).pTi + matrix[i][2]));
            }
        }
    }

    /**
     * Пройтись по всему графу от вершины с начальным индексом (indexStart) до вершины с конечным индексом (indexEnd)
     *      и пересчитать (получить) nTi элемент графа
     * @param matrix исходная матрица со всем вершинами и переходами, а также параметром tao
     * @param indexStart индекс стартовой першины от которой будет происходить обход
     * @param indexEnd индекс конечной вершины до которой будет происходить обход
     * @param size nTi для возможного пересчета для следующей вершины nTi
     */
    public static void findAllnTi(int[][] matrix, int indexStart, int indexEnd, int size) {
        for (int i = 0; i < matrix.length; i++) {
            if (indexStart == matrix[i][1]) {
                findAllnTi(matrix, matrix[i][0], indexEnd, checkNewnTi(matrix[i][0], size - matrix[i][2]));
            }
        }
    }

    /**
     * Получить элементы в массив, которые не являются ни начальным ни конечным
     * @param matrix матрица с входными числами
     */
    public static void getMidElem(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean dontZero = false, dontOne = false;
            for (int j = 0; j < elems.size(); j++) {
                if (matrix[i][0] == elems.get(j).i) {
                    dontZero = true;
                    break;
                }
            }
            if (!dontZero)
                elems.add(new Elem(matrix[i][0], 0, 0, 0));

        }
    }

    /**
     * Получить Параметр Ri для всех элементов (Ri = nTi - pTi)
     */
    public static void findAllRi() {
        for (Elem elem : elems)
            elem.Ri = elem.nTi - elem.pTi;
    }

    /**
     * Получить содержимое элемента ввиде строки
     * @param elem элемент для которого необходимо сформировать строку
     * @return строка с данными элемента
     */
    public static String getStrElem(Elem elem) {
        return "I = " + elem.i + "\npTi = " + elem.pTi + "\nnTi = " + elem.nTi + "\nRi = " + elem.Ri;
    }

    /**
     * Главная функция программы
     * @param argv
     */
    public static void main(String[] argv) {
        /**
         * массив ребер
         * 0 столбец - i (откуда идет вершина)
         * 1 столбец - j (куда входит вершина)
         * taoij - вес ребра
         */
        int[][] matrix = {
                {15, 8, 2},
                {14, 6, 1},
                {10, 2, 2},
                {17, 8, 2},
                {3, 14, 3},
                {2, 15, 3},
                {10, 14, 5},
                {2, 6, 4},
                {10, 3, 4},
                {6, 8, 4},
                {3, 6, 2},
                {6, 17, 1},
                {3, 17, 6}
        };

        /**
         * Получение начального, конечного и всех элементов между ними
         */
        elems.add(getIndexStart(matrix));
        elems.add(getIndexEnd(matrix));
        getMidElem(matrix);

        /**
         * Получение данных для каждого элемента сетевого графа
         * Для начала получаем pTi
         * затем считаем последний nTi
         * далее считаем все nTi
         * и наконец производим расчет Ri
         */
        findAllpTi(matrix, elems.get(0).i, elems.get(1).i, 0);
        elems.get(1).nTi = elems.get(1).pTi;
        findAllnTi(matrix, elems.get(1).i, elems.get(0).i, elems.get(1).nTi);
        findAllRi();

        /**
         * Запись результата в файл result.txt в формат, понятный graphWiz
         */
        try(FileWriter writer = new FileWriter("result.txt", false))
        {
            // запись всей строки
            String text = "digraph lab3 {";
            for (Elem elem : elems) {
                text += elem.i + " [label=\"" + "i = " + elem.i + "\npTi = " + elem.pTi + "\nnTi = " + elem.nTi + "\nRi = " + elem.Ri + "\"]; ";
            }
            for (int i = 0; i < matrix.length; i++)
                text += matrix[i][0] + "->" + matrix[i][1] + " [label=" + matrix[i][2] + "]; ";
            text += "}";
            writer.write(text);

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
