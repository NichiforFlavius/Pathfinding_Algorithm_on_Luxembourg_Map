public class Pair<val1, val2> {
    private val1 first;
    private val2 second;

    public Pair(val1 first, val2 second) {
        this.first = first;
        this.second = second;
    }

    public val1 getFirst() {
        return first;
    }

    public void setFirst(val1 first) {
        this.first = first;
    }

    public val2 getSecond() {
        return second;
    }

    public void setSecond(val2 second) {
        this.second = second;
    }
}