package com.paypal.reports.reportsextract.converter;

import com.paypal.infrastructure.util.TimeMachine;
import com.paypal.reports.reportsextract.model.HmcMiraklTransactionLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HMCMiraklTransactionLineToHmcFinancialReportLineConverterTest {

	private static final String ORDER_ID = "orderId";

	private static final String SELLER_ID = "sellerId";

	private static final String MIRAKL_TRANSACTION_LINE_ID = "miraklTransactionLineId";

	private static final String TRANSACTION_TYPE = "transactionType";

	private static final String PAYAMENT_TRANSACTION_ID = "payamentTransactionId";

	@InjectMocks
	private MiraklTransactionLineToFinancialReportLineConverter testObj;

	@Test
	void convert_shouldConvertMiraklTransactionLineIntoFinancialReportLineWhenAllAttributesAreFilled() {
		TimeMachine.useFixedClockAt(LocalDateTime.of(2020, 11, 10, 20, 45));
		final LocalDateTime now = TimeMachine.now();
		//@formatter:off
        final HmcMiraklTransactionLine HmcMiraklTransactionLineStub = HmcMiraklTransactionLine.builder()
                .orderId(ORDER_ID)
                .sellerId(SELLER_ID)
                .transactionLineId(MIRAKL_TRANSACTION_LINE_ID)
                .transactionTime(now)
                .transactionType(TRANSACTION_TYPE)
				.amount(BigDecimal.ONE)
                .creditAmount(BigDecimal.TEN)
				.debitAmount(BigDecimal.ZERO)
                .transactionNumber(PAYAMENT_TRANSACTION_ID)
                .build();
        //@formatter:on

		final var result = testObj.convert(HmcMiraklTransactionLineStub);
		assertThat(result.getMiraklOrderId()).isEqualTo(ORDER_ID);
		assertThat(result.getMiraklSellerId()).isEqualTo(SELLER_ID);
		assertThat(result.getMiraklTransactionLineId()).isEqualTo(MIRAKL_TRANSACTION_LINE_ID);
		assertThat(result.getMiraklTransactionTime()).isEqualTo(now);
		assertThat(result.getMiraklTransactionType()).isEqualTo(TRANSACTION_TYPE);
		assertThat(result.getMiraklCreditAmount()).isEqualTo(BigDecimal.TEN);
		assertThat(result.getMiraklDebitAmount()).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	void convert_shouldReturnNullWhenMiraklTransactionLineIsNull() {

		final var result = testObj.convert(null);
		assertThat(result).isNull();

	}

}
