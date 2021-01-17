package com.utils.json;

public class JsonResponse
{
   public static String error(String error)
   {
       JsonObject object = new JsonObject();

       object.addProperty("hasError", true);
       object.addProperty("error", error);
       object.addProperty("data", (Jsonable) null);

       return object.toJson();
   }

   public static String data(Jsonable data)
   {
       JsonObject object = new JsonObject();

       object.addProperty("hasError", false);
       object.addProperty("error", "");
       object.addProperty("data", data);

       return object.toJson();
   }
}
