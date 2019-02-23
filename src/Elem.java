/**
 * Класс содержащий одну вершину сетевого графа
 */
public class Elem {
    int i = 0;
    int pTi = 0;
    int nTi = 0;
    int Ri = 0;

    /**
     * Конструктор вершины сетевого графа с параметрами
     * @param i индекс вершины
     * @param pTi момент времени (ранний срок свершения события)
     * @param nTi момент времени (поздний срок свершения события)
     * @param Ri резевр времени для события
     */
    public Elem(int i, int pTi, int nTi, int Ri) {
        this.i = i;
        this.pTi = pTi;
        this.nTi = nTi;
        this.Ri = Ri;
    }
}
