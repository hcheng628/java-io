package us.hcheng.javaio.annotation.generic.annotation;

import us.hcheng.javaio.annotation.generic.entity.Animal;
import us.hcheng.javaio.annotation.generic.entity.Dog;
import us.hcheng.javaio.annotation.generic.entity.Organism;
import us.hcheng.javaio.annotation.generic.entity.Cat;

import java.lang.reflect.Field;
import java.util.*;

public class App {

    public static void main(String[] args) {
        App app = new App();

        Cat cat = new Cat("Cat");
        Dog dog = new Dog("Dog");
        Animal animal = new Animal("Animal");
        Organism organism = new Organism("Organism");

        app.checkClassAnn(cat.getClass());
        app.checkClassAnn(dog.getClass());
        app.checkClassAnn(animal.getClass());
        app.checkClassAnn(organism.getClass());
    }

    private void checkClassAnn(Class<?> klass) {
        ClassAnn classAnn = klass.getAnnotation(ClassAnn.class);
        System.out.println(classAnn == null ? "default: " + klass.getSimpleName().toUpperCase() :
                "custom: " + classAnn.tableName());

        List<Field> fields = new ArrayList<>();
        Queue<Class> queue = new LinkedList<>(Arrays.asList(klass));

        while (!queue.isEmpty()) {
            Class currClass = queue.poll();
            fields.addAll(Arrays.stream(currClass.getDeclaredFields()).toList());
            Class par = currClass.getSuperclass();
            if (par != null)
                queue.offer(par);
        }

        for (Field f : fields) {
            FieldAnn fieldAnn = f.getAnnotation(FieldAnn.class);
            System.out.println(fieldAnn == null ? "default: " + f.getName() : "custom: " + fieldAnn.fieldName());
        }
        System.out.println("***********************");
    }

}
