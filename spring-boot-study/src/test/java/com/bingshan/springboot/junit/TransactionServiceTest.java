package com.bingshan.springboot.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("交易服务测试")
public class TransactionServiceTest {

    @Nested
    @DisplayName("用户交易测试")
    class UserTransactionTest {

        @Nested
        @DisplayName("正向测试用例")
        class PositiveCase {

            @Test
            @DisplayName("交易检查应通过")
            public void shouldPassCheckWhenParameterValid() {

            }
        }

        @Nested
        @DisplayName("负向测试用例")
        class NegativeCase {

        }
    }

    @Nested
    @DisplayName("商家交易测试")
    class CompanyTransactionTest {

    }
}
