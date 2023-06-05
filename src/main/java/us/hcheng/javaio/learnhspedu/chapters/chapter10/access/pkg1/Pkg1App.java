package us.hcheng.javaio.learnhspedu.chapters.chapter10.access.pkg1;

public class Pkg1App {

    public static void main(String[] args) {
        Base base = new Base();

        // non-static
        base.publicM();
        int a = base.publicVal;
        base.protectedM();
        a = base.protectedVal;
        base.m();
        a = base.val;

        // static
        a = Base.staticPublicVal;
        a = Base.staticProtectedVal;
        a = Base.staticVal;
        Base.staticPublicM();
        Base.staticProtectedM();
        Base.staticM();
    }

    private void test() {
        Base base = new Base();

        // non-static
        base.publicM();
        int a = base.publicVal;
        base.protectedM();
        a = base.protectedVal;
        base.m();
        a = base.val;

        // static
        a = Base.staticPublicVal;
        a = Base.staticProtectedVal;
        a = Base.staticVal;
        Base.staticPublicM();
        Base.staticProtectedM();
        Base.staticM();
    }
}
