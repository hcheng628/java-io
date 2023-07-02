package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BillServiceTest {

    private BillService billService;

    @BeforeEach
    void setup() {
        billService = new BillService();
    }

    @Test
    void testPayBill() {
        billService.payBill(1, "支付宝");
    }

}
