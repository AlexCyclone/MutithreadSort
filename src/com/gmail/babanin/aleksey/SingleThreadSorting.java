package com.gmail.babanin.aleksey;

public class SingleThreadSorting implements Runnable {
    private int[] array;
    private int begin;
    private int end;
    private Thread thr;
    private int index;
    private boolean stop = false;

    public SingleThreadSorting(int[] array, int begin, int end) {
        super();
        this.array = array;
        this.begin = begin;
        this.end = end;
        thr = new Thread(this);
        thr.start();
        this.index = begin;
    }

    public Thread getThr() {
        return thr;
    }

    public int peekElement() {
        return array[index];
    }

    public int pollElement() {
        int temp = array[index];
        this.index++;
        if (this.index >= this.end) {
            this.stop = true;
        }
        return temp;
    }

    public boolean isStop() {
        return stop;
    }

    @Override
    public void run() {
        int[] k = { 1, 4, 10, 23, 57, 145, 356, 911, 1968, 4711, 11969, 27901, 84801, 213331, 543749, 1355339, 3501671,
                8810089, 21521774, 58548857, 157840433, 410151271, 1131376761, 2147483647 };
        int ki = k.length - 1;
        while (end - begin < k[ki]) {
            ki -= 1;
        }
        while (ki >= 0) {
            for (int i = k[ki] + begin; i < end; i += 1) {
                int j = i;
                int temp = array[i];
                while ((j >= k[ki] + begin) && (array[j - k[ki]] > temp)) {
                    array[j] = array[j - k[ki]];
                    j -= k[ki];
                }
                array[j] = temp;
            }
            ki -= 1;
        }
    }

}
