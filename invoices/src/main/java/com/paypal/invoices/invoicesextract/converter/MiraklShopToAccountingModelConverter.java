package com.paypal.invoices.invoicesextract.converter;

import com.mirakl.client.mmp.domain.common.MiraklAdditionalFieldValue;
import com.mirakl.client.mmp.domain.shop.MiraklShop;
import com.paypal.infrastructure.converter.Converter;
import com.paypal.invoices.infraestructure.configuration.InvoicesHyperwalletApiConfig;
import com.paypal.invoices.invoicesextract.model.AccountingDocumentModel;
import com.paypal.invoices.invoicesextract.model.InvoiceModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class to convert from {@link MiraklShop} to {@link InvoiceModel}
 */
@Service
public class MiraklShopToAccountingModelConverter implements Converter<MiraklShop, AccountingDocumentModel> {

	protected final InvoicesHyperwalletApiConfig invoicesHyperwalletApiConfig;

	public MiraklShopToAccountingModelConverter(final InvoicesHyperwalletApiConfig invoicesHyperwalletApiConfig) {
		this.invoicesHyperwalletApiConfig = invoicesHyperwalletApiConfig;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountingDocumentModel convert(final @NonNull MiraklShop source) {
		final List<MiraklAdditionalFieldValue> additionalFieldValues = source.getAdditionalFieldValues();
		//@formatter:off
		return getBuilder()
				.hyperwalletProgram(source.getAdditionalFieldValues())
				.shopId(source.getId())
				.destinationToken(additionalFieldValues)
				.build();
		//@formatter:on
	}

	@SuppressWarnings("java:S3740")
	protected AccountingDocumentModel.Builder getBuilder() {
		return AccountingDocumentModel.builder();
	}

}
