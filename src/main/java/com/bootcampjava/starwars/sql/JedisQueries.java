package com.bootcampjava.starwars.sql;

public class JedisQueries {
    public static final String ID = "id";
    public static final String Name = "name";
    public static final String STRENGTH = "strength";
    public static final String VERSION = "version";

    public static final String INSERT = "INSERT INTO jedis (ID, NAME, STRENGTH, VERSION) VALUES(:id, :name, :strength, :version";
}
