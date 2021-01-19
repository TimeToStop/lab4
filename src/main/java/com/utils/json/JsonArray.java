package com.utils.json;


public class JsonArray implements Jsonable
{
    private final StringBuilder builder = new StringBuilder();

    public JsonArray()
    {
    }

    public void append(boolean value)
    {
        builder.append(",").append(value ? "true" : "false");
    }

    public void append(int value)
    {
        builder.append(",").append(value);
    }

    public void append(double value)
    {
        builder.append(",").append(value);
    }

    public void append(String value)
    {
        builder.append(",\"").append(value).append("\"");
    }

    public void append(Jsonable value)
    {
        builder.append(",").append(value.toJson());
    }

    @Override
    public String toJson()
    {
        if (builder.length() != 0 && builder.charAt(0) == ',') builder.deleteCharAt(0);
        return "[" + builder.toString() + "]";
    }


    public static <T extends Jsonable> JsonArray from(Iterable<T> collection)
    {
        JsonArray array = new JsonArray();

        for(T element : collection)
        {
            array.append(element);
        }

        return array;
    }
}
