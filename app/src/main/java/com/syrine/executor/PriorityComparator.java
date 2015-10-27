package com.syrine.executor;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Runnable> {
    @Override
    public int compare(Runnable lhs, Runnable rhs) {
        if (lhs instanceof Priority && rhs instanceof Priority) {
            int pL = ((Priority) lhs).getPriority();
            int pR = ((Priority) rhs).getPriority();

            if (pL > pR) return 1;
            if (pL < pR) return -1;
            return 0;
        }
        return 0;
    }
}
