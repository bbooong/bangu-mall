package com.github.bbooong.bangumall.stock.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.github.bbooong.bangumall.stock.exception.StockQuantityNotEnoughException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("단위 테스트: Stock")
class StockTest {

    @Nested
    @DisplayName("재고의 수량을 차감할 때")
    class Describe_decreaseQuantity {

        final Stock notEnoughStock = Stock.create(1L, 10, LocalDate.now());

        @Nested
        @DisplayName("재고의 수량이 부족하면")
        class Context_With_NotEnoughQuantity {

            final Quantity overQuantity = Quantity.create(20);

            @Test
            @DisplayName("예외가 발생한다.")
            void it_throws_exception() {
                assertThatCode(() -> notEnoughStock.decreaseQuantity(overQuantity))
                        .isExactlyInstanceOf(StockQuantityNotEnoughException.class)
                        .hasMessage("수량이 부족합니다.");
            }
        }
    }
}
