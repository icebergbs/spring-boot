package com.bingshan.springboot.junit;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Stream;

@DisplayName("售票器类型测试")
public class ExchangeRateConverterTest {
    private TicketSeller ticketSeller = new TicketSeller();

    Stream<DynamicTest> oddNumberDynamicTestWithStream() {

        return Stream.of(
                Lists.list("提前购票", LocalTime.of(12, 20, 24, 0), true),
                Lists.list("准点购票", LocalTime.of(12, 20, 24, 0), true),
                Lists.list("晚点购票", LocalTime.of(12, 20, 24, 0), true)
        ).map(data -> DynamicTest.dynamicTest((String)data.get(0),
                () -> Assertions.assertEquals(ticketSeller.cloudSellAt((ArrayList) data.get(1)),  data.get(2))
        ));
    }
}
