package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.frontend.service.Page;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Route("deposit")
public class DepositView extends AbstractView {
    public DepositView() {
        showHeader(Page.DEPOSIT);
        initDepositPage();
    }

    private void initDepositPage() {
        HorizontalLayout balanceLayout = new HorizontalLayout();
        balanceLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        balanceLayout.add(new Label("Сумма к пополнению: "));
        BigDecimalField balanceField = new BigDecimalField();
        balanceField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        balanceField.setValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        balanceLayout.add(balanceField);
        balanceLayout.add(new Button("Внести", e -> {
            user.setBalance(user.getBalance().add(balanceField.getValue()));
            UI.getCurrent().getPage().reload();
        }));

        add(balanceLayout);
    }
}
