package com.wex.transys.kafkaPOC.model;

public class Count
{
    private int count;

    Count()
    {

    }

    public Count(int count)
    {
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }

    @Override
    public String toString()
    {
        return "Count{" + "count=" + count + '}';
    }
}
