package com.funwords.worldssports;

import java.util.ArrayList;

public class FindAdjacents {

    public FindAdjacents() {
    }

    public boolean isValidPos(int i, int j, int n)
    {
        if (i < 0 || j < 0 || i > n - 1 || j > n - 1)
            return false;

        return true;
    }

    public ArrayList<String> adjacentElements(int i, int j)
    {
        int n = 5;
        ArrayList<String> list  = new ArrayList<>();
        // first row
        if (isValidPos(i - 1, j - 1, n))
            list.add((i - 1)+""+(j - 1));
        if (isValidPos(i - 1, j, n))
            list.add((i - 1)+""+j);
        if (isValidPos(i - 1, j + 1, n))
            list.add((i - 1)+""+(j + 1));
        // second row
        if (isValidPos(i, j - 1, n))
            list.add(i +""+(j - 1));
        if (isValidPos(i, j + 1, n))
            list.add(i +""+(j + 1));
        // third row
        if (isValidPos(i + 1, j - 1, n))
            list.add((i + 1)+""+(j - 1));
        if (isValidPos(i + 1, j, n))
            list.add((i + 1)+""+j);
        if (isValidPos(i + 1, j + 1, n))
            list.add((i + 1)+""+(j + 1));

        System.out.println(list);

        return list;
    }

}
