package us.hcheng.javaio.annotation.generic.reflection;

import us.hcheng.javaio.annotation.generic.entity.Organism;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodApp {

    public static void main(String[] args) throws Exception {
        MethodApp app = new MethodApp();
        // app.doIt();
        app.freedomGeneric();
    }

    public void doIt() throws Exception {
        Class<?> klass = Class.forName("us.hcheng.javaio.annotation.generic.entity.Organism");

        Constructor<?> const1 = klass.getConstructor(String.class);
        Organism o = (Organism) const1.newInstance("Moka");
        System.out.println(o);

        Method setNameMethod = klass.getDeclaredMethod("setName", String.class);
        if (!setNameMethod.canAccess(o))
            setNameMethod.setAccessible(true);

        setNameMethod.invoke(o, "Cheng");

        System.out.println(o);

        for (Method m : klass.getMethods()) {
            System.out.println(m.getName() + " has params: " + m.getParameterCount());
            for (Class<?> c : m.getParameterTypes()) {
                System.out.println(c);
            }
        }

        System.out.println(klass.getConstructor().newInstance());

        Constructor<?> const2 = klass.getConstructor(String.class, Integer.TYPE);
        System.out.println(const2.newInstance("Moka", 5));
    }

    public void freedomGeneric() throws Exception {
        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        Method addIt = List.class.getMethod("add", Object.class);
        addIt.invoke(list, new Organism("Moka", 5));
        addIt.invoke(list, "Kobe...");
        addIt.invoke(list, 30.26D);

        for (Object o : list)
            System.out.println(o);
    }

}
