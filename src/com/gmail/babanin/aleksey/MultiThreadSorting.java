package com.gmail.babanin.aleksey;

public class MultiThreadSorting {
    static void sort(int[] array, int threadNumber) {
        if (threadNumber > array.length) {
            threadNumber = array.length;
        }

        SingleThreadSorting[] threadarray = new SingleThreadSorting[threadNumber];
        for (int i = 0; i < threadarray.length; i++) {
            int size = array.length / threadNumber;
            int begin = size * i;
            int end = ((i + 1) * size);
            if (i == threadarray.length - 1) {
                end = array.length;
            }
            threadarray[i] = new SingleThreadSorting(array, begin, end);
        }
        for (int i = 0; i < threadarray.length; i++) {
            try {
                threadarray[i].getThr().join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        if (threadNumber > 1) {
            System.arraycopy(mergeArrays(array, threadarray), 0, array, 0, array.length);
        }
    }

    private static int[] mergeArrays(int[] array, SingleThreadSorting[] threadarray) {
        int[] arr = new int[array.length];
        for (int i = 0; i < arr.length; i++) {
            int min = Integer.MAX_VALUE;
            int k = -1;
            for (int j = 0; j < threadarray.length; j++) {
                if (!threadarray[j].isStop() && min > threadarray[j].peekElement()) {
                    min = threadarray[j].peekElement();
                    k = j;
                }
            }
            if (k != -1) {
                arr[i] = threadarray[k].pollElement();
            }
        }
        return arr;
    }

}