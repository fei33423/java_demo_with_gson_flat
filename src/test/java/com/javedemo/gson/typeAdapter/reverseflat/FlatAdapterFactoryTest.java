package com.javedemo.gson.typeAdapter.reverseflat;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class FlatAdapterFactoryTest {
    private class ClassFlat {
        @ReverseFlatten("x::y")
        int testY;
        @ReverseFlatten("x::z")
        int testZ;
        @SerializedName("no")
        Integer testNo;
        @ReverseFlatten("array::0::y")
        int testArrayY;
        @ReverseFlatten("deep::0::10::0::x::1::z")
        String testArrayZ;
    }

    private class ClassInner {
        int y;
        int z;

        @Override
        public String toString() {
            return "ClassInner{" +
                    "y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    private class ClassComplex {
        Integer y;
        Integer testY;
        Integer testZ;
        Integer no;
        ClassInner x;
        List<ClassInner> array;
        JsonArray deep;

        @Override
        public String toString() {
            return "ClassComplex{" +
                    "y=" + y +
                    ", testY=" + testY +
                    ", testZ=" + testZ +
                    ", no=" + no +
                    ", x=" + x +
                    ", array=" + array +
                    ", deep=" + deep +
                    '}';
        }
    }

    @Test
    public void test_serialize_two() {
        ClassFlat one = new ClassFlat();
        one.testY = 13;
        one.testY = 666;
        one.testNo = -1;
        one.testArrayY = 42;
        one.testArrayZ = "foo";
        final Gson gson = Helper.createFlatteningGson();
        final Gson gson_default = Helper.createDefaultGson();

        String res = gson.toJson(one);
        System.out.println("res="+res);
        Assert.assertNotNull(res);
        assertNotEquals("", res);
        ClassComplex complex = gson_default.fromJson(res, ClassComplex.class);
        assertNotNull(complex.x);
        assertEquals(one.testY, complex.x.y);
        assertEquals(one.testZ, complex.x.z);
        assertEquals(one.testNo, complex.no);
        assertEquals(one.testArrayY, complex.array.get(0).y);
        assertNull(complex.testY);
        assertNull(complex.testZ);
        assertNull(complex.y);

        assertEquals(1, complex.deep.size());
        assertEquals(JsonArray.class, complex.deep.get(0).getClass());
        assertEquals("foo", complex.deep
                .get(0).getAsJsonArray()
                .get(10).getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("x").getAsJsonArray()
                .get(1).getAsJsonObject()
                .get("z").getAsString());
    }


    @Test
    public void test1() {
        Gson defaultGson = Helper.createDefaultGson();
        FlattenUserSubscription fu = new FlattenUserSubscription();
        Address addRess=new Address();
        addRess.setCity("city1");

        fu.setAddress(addRess);
        Address addRess1=new Address();
        addRess1.setCity("city2");
        Home home=new Home();
        home.setHomeOwner("homeOwner");
        addRess1.setHome(home);
        fu.setAddress1(addRess1);


        String s = defaultGson.toJson(fu);
        System.out.println(s);
    }

}