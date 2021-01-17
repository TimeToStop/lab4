package com.utils.json;

public class JsonObject implements Jsonable
{
    private final StringBuilder builder = new StringBuilder();

    public JsonObject()
    {
    }

    public void addProperty(String name, boolean value)
    {
        builder.append(",\"").append(name).append("\":")
                .append(value ? "true" : "false");
    }

    public void addProperty(String name, int value)
    {
        builder.append(",\"").append(name).append("\":").append(value);
    }

    public void addProperty(String name, double value)
    {
        builder.append(",\"").append(name).append("\":").append(value);
    }

    public void addProperty(String name, String value)
    {
        builder.append(",\"").append(name).append("\":");

        if (value == null) {
            builder.append("null");
        } else {
            builder.append("\"").append(value).append("\"");
        }
    }

    public void addProperty(String name, Jsonable value)
    {
        builder.append(",\"").append(name).append("\":")
                .append(value == null ? "null" : value.toJson());
    }

    @Override
    public String toJson()
    {
        if (builder.length() != 0 && builder.charAt(0) == ',') builder.deleteCharAt(0);
        return "{" + builder.toString() + "}";
    }
}
