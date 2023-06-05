package us.hcheng.javaio.learnhspedu.chapters.chapter10.access.pkg1;

public class SubSamePkg extends Base {

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

//      via the parent relationship, it will get protected vars and methods
//      but since this is in the same pkg .... so it get all excepts private
        a = this.publicVal;
        a = this.protectedVal;
        a = this.val;
        this.publicM();
        this.protectedM();
        this.m();
    }

}
