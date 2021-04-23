package com.wex.transys.kafkaPOC.model;

public class User
{
    String name;

    User()
    {

    }

    public User(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }


    @Override
    public String toString()
    {
        return "User{" + "name='" + name + '\'' + '}';
    }
}
