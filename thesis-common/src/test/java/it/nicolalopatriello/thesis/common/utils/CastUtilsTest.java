package it.nicolalopatriello.thesis.common.utils;


import it.nicolalopatriello.thesis.common.exception.CastUtilsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CastUtilsTest {

    @Test
    void upcast() throws CastUtilsException {

        A a = new A(1, 2, 3);
        B b = new B(1, 2, 3, 4, 5, 6);
        C c = new C(1, 2, 3, 4, 5, 6, 7, 8, 9);

        B b2 = CastUtils.upcast(a, B.class);
        assertEquals(a.getA(), b2.getA());
        assertEquals(a.getB(), b2.getB());
        assertEquals(a.getC(), b2.getC());
        assertNull(b2.getD());
        assertNull(b2.getE());
        assertNull(b2.getF());

        C c2 = CastUtils.upcast(a, C.class);
        assertEquals(a.getA(), c2.getA());
        assertEquals(a.getB(), c2.getB());
        assertEquals(a.getC(), c2.getC());
        assertNull(c2.getD());
        assertNull(c2.getE());
        assertNull(c2.getF());
        assertNull(c2.getG());
        assertNull(c2.getH());
        assertNull(c2.getI());

        C c3 = CastUtils.upcast(b, C.class);
        assertEquals(b.getA(), c3.getA());
        assertEquals(b.getB(), c3.getB());
        assertEquals(b.getC(), c3.getC());
        assertEquals(b.getD(), c3.getD());
        assertEquals(b.getE(), c3.getE());
        assertEquals(b.getF(), c3.getF());
        assertNull(c2.getG());
        assertNull(c2.getH());
        assertNull(c2.getI());

        C c4 = CastUtils.upcast(c, C.class);
        assertEquals(c.getA(), c4.getA());
        assertEquals(c.getB(), c4.getB());
        assertEquals(c.getC(), c4.getC());
        assertEquals(c.getD(), c4.getD());
        assertEquals(c.getE(), c4.getE());
        assertEquals(c.getF(), c4.getF());
        assertEquals(c.getG(), c4.getG());
        assertEquals(c.getH(), c4.getH());
        assertEquals(c.getI(), c4.getI());
    }

    @Test
    void downcast() throws CastUtilsException, CastUtilsException {

        A a = new A(1, 2, 3);
        B b = new B(1, 2, 3, 4, 5, 6);
        C c = new C(1, 2, 3, 4, 5, 6, 7, 8, 9);

        B b2 = CastUtils.downcast(c, B.class);
        assertEquals(c.getA(), b2.getA());
        assertEquals(c.getB(), b2.getB());
        assertEquals(c.getC(), b2.getC());
        assertEquals(c.getD(), b2.getD());
        assertEquals(c.getE(), b2.getE());
        assertEquals(c.getF(), b2.getF());

        A a2 = CastUtils.downcast(c, A.class);
        assertEquals(c.getA(), a2.getA());
        assertEquals(c.getB(), a2.getB());
        assertEquals(c.getC(), a2.getC());


        A a3 = CastUtils.downcast(b, A.class);
        assertEquals(b.getA(), a3.getA());
        assertEquals(b.getB(), a3.getB());
        assertEquals(b.getC(), a3.getC());

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    private static class A {
        private Integer a;
        protected Integer b;
        public Integer c;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    private static class B extends A {
        private Integer d;
        protected Integer e;
        public Integer f;

        public B(Integer a, Integer b, Integer c, Integer d, Integer e, Integer f) {
            super(a, b, c);
            this.d = d;
            this.e = e;
            this.f = f;
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    private static class C extends B {
        private Integer g;
        protected Integer h;
        public Integer i;

        public C(Integer a, Integer b, Integer c, Integer d, Integer e, Integer f, Integer g, Integer h, Integer i) {
            super(a, b, c, d, e, f);
            this.g = g;
            this.h = h;
            this.i = i;
        }
    }
}