package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.repository.ProductRepository;
import com.github.novikovmn.spring2.service.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("market")
public class MarketView extends AbstractView{
    private final CartService cartService;
    private final ProductRepository productRepository;

    public MarketView(CartService cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        initMarketPage();
    }

    private void initMarketPage() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new Button("Главная", e -> System.out.println("Главная")));

        add(horizontalLayout);
    }
}
