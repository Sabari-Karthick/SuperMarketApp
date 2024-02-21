package com.vanenburg.sm.constants;

public class PathConstants {
	public static final String API_V1 = "/api/v1";
	public static final String ORDER = "/order";
	public static final String ORDERS = "/orders";
	public static final String PRODUCTS = "/products";
	public static final String USER = "/user";

	public static final String API_V1_STAFF = API_V1 + "/staff";
	public static final String API_V1_ORDER = API_V1 + ORDER;
	public static final String API_V1_PRODUCTS = API_V1 + PRODUCTS;
	public static final String API_V1_AUTH = API_V1 + "/auth";
	public static final String API_V1_USER = API_V1 + "/user";
	public static final String ADD = "/add";
	public static final String REGISTER = "/register";
	public static final String EDIT = "/edit";
	public static final String IMPORT = "/import";
	public static final String EXPORT = "/export";
	public static final String CART = "/cart";
	public static final String LOGIN = "/login";

	public static final String PRODUCT_ID = "/{productId}";
	public static final String USER_ID = "/{userId}";
	public static final String ADD_LOYALTY_CARD = ADD + "loyalty/{userId}";
	public static final String LOYALTY_CARD = "loyalty/{userId}";
	public static final String DELETE_BY_PRODUCT_ID = "/delete/{productId}";
	public static final String DELETE_BY_USER_ID = "/delete/user/{userId}";
	public static final String SEARCH = "/search";
	public static final String CHECK = "/check";
	public static final String CHECK_STOCK = CHECK + "/stock";
	public static final String CHECK_EXPIRY = CHECK + "/expiry";
	public static final String APPLY_DISCOUNTS = "/apply/discounts/{discountName}";
	public static final String APPLY_DISCOUNTS_ON_TOTAL = "/apply/discounts/{discountName}/{totalPrice}";
	public static final String CHECK_DISCOUNTS = CHECK + "/discounts";
	public static final String SEARCH_CATEGORY = SEARCH + "/category";
	public static final String IDS = "/ids";
	public static final String ALL = "/all";
	public static final String STATS = "/top/sell";

	public static final String ORDER_ID = "/{orderId}";
	public static final String REVENUE = "/revenue";
	public static final String ORDER_ID_ITEMS = ORDER_ID + "/items";
	public static final String ORDER_ID_REFUND = ORDER_ID + "/refund";
	public static final String ADD_DISCOUNT = ADD +"/discount";
	public static final String APPLY_POINTS = "/apply/points/{userId}";
	public static final String ADD_POINTS = ADD+ "/points/{userId}";

}
