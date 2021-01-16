package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.domain.Category;
import com.github.novikovmn.spring2.domain.Price;
import com.github.novikovmn.spring2.domain.Product;
import com.github.novikovmn.spring2.frontend.service.Page;
import com.github.novikovmn.spring2.frontend.service.custom_components.TextFieldWithPlaceholder;
import com.github.novikovmn.spring2.repository.CategoryRepository;
import com.github.novikovmn.spring2.repository.ProductRepository;
import com.github.novikovmn.spring2.service.CartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Route("market")
public class MarketView extends AbstractView{
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final List<Product> productList;
    private final List<Category> categories;

    public MarketView(CartService cartService, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        productList = productRepository.findAll();
        categories = categoryRepository.findAll();
        showHeader(Page.MARKET);
        initMarketPage();
    }

    private void initMarketPage() {
        Grid<Product> productGrid = new Grid<>(Product.class);
        productGrid.setWidth("90%");
        productGrid.setColumns("title", "price");
        productGrid.getColumnByKey("title").setFlexGrow(1);
        productGrid.setItems(productList);
        /**
         * История цен: начало
         */
        Grid.Column<Product> priceHistoryColumn = productGrid.addColumn(new ComponentRenderer<>(item -> {
            VerticalLayout priceHistoryLayout = new VerticalLayout();
            List<Price> priceHistory = item.getPriceHistory();
            for (Price price : priceHistory) {
                priceHistoryLayout.add(new Label(price.toString()));
            }
            return priceHistoryLayout;
        }));
        /**
         * конец
         */
        priceHistoryColumn.setHeader("История цен");
        productGrid.addColumn(new ComponentRenderer<>(item ->{
            IntegerField quantityField = new IntegerField();
            quantityField.setValue(1);
            quantityField.setHasControls(true);
            quantityField.setMin(1);
            Button addInCartButton = new Button("\uD83D\uDED2", action -> {
                cartService.add(item, quantityField.getValue());
                quantityField.setValue(1);
                Notification.show("Продукт добавлен в корзину");
            });
            return new HorizontalLayout(quantityField, addInCartButton);
        }));

        CheckboxGroup<Category> categoryCheckboxGroup = categoryCheckboxGroup();
        categoryCheckboxGroup.addValueChangeListener(e -> productGrid.setItems(
                filter(new ArrayList<>(categoryCheckboxGroup.getSelectedItems()))));

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, productGrid);

        add(categoryCheckboxGroup, productGrid);

        if (isManager()) {
            add(productAddLayout());
        }
    }

    private CheckboxGroup<Category> categoryCheckboxGroup() {
        CheckboxGroup<Category> group = new CheckboxGroup<>();
        group.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        group.setItems(categories);
        group.setLabel("Категории");
        group.setItemLabelGenerator(category -> category.getTitle());
        return group;
    }

    private HorizontalLayout productAddLayout() {
        HorizontalLayout content = new HorizontalLayout();
        TextFieldWithPlaceholder titleField = new TextFieldWithPlaceholder("Наименование");
        BigDecimalField priceField = new BigDecimalField();
        priceField.setValue(BigDecimal.ZERO.setScale(2));
        CheckboxGroup<Category> categoryCheckboxGroup = categoryCheckboxGroup();
        Button addButton = new Button("Сохранить", e -> {
            if (titleField.isEmpty() || categoryCheckboxGroup.getSelectedItems().isEmpty()) {
                Notification.show("Наименование должно быть указано. Категории должны быть выбраны");
            } else {
                Product product = new Product();
                product.setTitle(titleField.getValue());
                product.setPrice(priceField.getValue());
                product.setCategories(new ArrayList<>(categoryCheckboxGroup.getSelectedItems()));
                productRepository.save(product);
                UI.getCurrent().getPage().reload();
                Notification.show("Новый товар успешно добавлен в магазин");
            }
        });
        content.add(titleField, priceField, categoryCheckboxGroup, addButton);

        return content;
    }

    private List<Product> filter(List<Category> categories) {
        if (categories.isEmpty()) {
            return productList;
        } else {
            List<Product> products = new ArrayList<>();
            for (Product product : productList) {
                for (Category category : categories) {
                    if (product.getCategories().contains(category)) {
                        products.add(product);
                    }
                }
            }
            return products;
        }
    }
}
