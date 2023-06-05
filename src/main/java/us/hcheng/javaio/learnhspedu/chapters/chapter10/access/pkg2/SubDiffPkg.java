package us.hcheng.javaio.learnhspedu.chapters.chapter10.access.pkg2;

import us.hcheng.javaio.learnhspedu.chapters.chapter10.access.pkg1.Base;

public class SubDiffPkg extends Base {

    public static void main(String[] args) {
        Base base = new Base();

        // non-static
        base.publicM();
        int a = base.publicVal;
//        base.protectedM();
//        a = base.protectedVal;
//        base.m();
//        a = base.val;

        // static
        a = Base.staticPublicVal;
        a = Base.staticProtectedVal;
//        a = Base.staticVal;
        Base.staticPublicM();
        Base.staticProtectedM();
//        Base.staticM();
    }

    private void test() {
        Base base = new Base();

        // non-static
        base.publicM();
        int a = base.publicVal;

//      this is due to they are in different packages
//        base.protectedM();
//        a = base.protectedVal;
//        base.m();
//        a = base.val;

//      because of the parent child relationship so we can access protected vars and methods
        a = this.protectedVal;
        this.protectedM();

        // static
        a = Base.staticPublicVal;
        a = Base.staticProtectedVal;


//      this is due to they are in different packages
//        a = Base.staticVal;
        Base.staticPublicM();
        Base.staticProtectedM();
//        Base.staticM();
    }

}
