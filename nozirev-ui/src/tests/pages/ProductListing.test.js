import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore, combineReducers, applyMiddleware } from 'redux';
import { thunk } from 'redux-thunk';
import { MemoryRouter } from 'react-router-dom';
import ProductListing from '../../pages/ProductListing';
import productReducer from '../../redux/reducers/productReducer';
import cartReducer from '../../redux/reducers/cartReducer';

const createTestStore = (productState = { products: [], loading: false, error: null }) =>
  createStore(
    combineReducers({ products: productReducer, cart: cartReducer }),
    { products: productState, cart: { items: [], totalCount: 0 } },
    applyMiddleware(thunk)
  );

const renderProductListing = (category = 'smartphones', store = createTestStore()) =>
  render(
    <Provider store={store}>
      <MemoryRouter>
        <ProductListing category={category} />
      </MemoryRouter>
    </Provider>
  );

const mockProducts = [
  { id: 1, productName: 'iPhone 11', imageUrl: 'https://example.com/img.jpg', amount: 599, description: 'A great phone' },
  { id: 2, productName: 'Samsung Galaxy', imageUrl: 'https://example.com/img2.jpg', amount: 499, description: 'Another great phone' },
];

describe('ProductListing Component', () => {
  test('renders category name as heading', () => {
    renderProductListing('smartphones');
    expect(screen.getByText('smartphones')).toBeInTheDocument();
  });

  test('renders tablets category heading', () => {
    renderProductListing('tablets');
    expect(screen.getByText('tablets')).toBeInTheDocument();
  });

  test('defaults to smartphones category', () => {
    renderProductListing();
    expect(screen.getByText('smartphones')).toBeInTheDocument();
  });

  test('shows loading state', () => {
    const store = createTestStore({ products: [], loading: true, error: null });
    renderProductListing('smartphones', store);
    expect(screen.getByText(/Loading products/i)).toBeInTheDocument();
  });

  test('does not show loading when not loading', () => {
    renderProductListing('smartphones');
    expect(screen.queryByText(/Loading products/i)).not.toBeInTheDocument();
  });

  test('shows error message on error', () => {
    const store = createTestStore({ products: [], loading: false, error: 'Network error' });
    renderProductListing('smartphones', store);
    expect(screen.getByText(/Network error/i)).toBeInTheDocument();
  });

  test('shows no products message when list is empty', () => {
    renderProductListing('smartphones');
    expect(screen.getByText(/No products available/i)).toBeInTheDocument();
  });

  test('does not show no-products message when products exist', () => {
    const store = createTestStore({ products: mockProducts, loading: false, error: null });
    renderProductListing('smartphones', store);
    expect(screen.queryByText(/No products available/i)).not.toBeInTheDocument();
  });

  test('renders product names', () => {
    const store = createTestStore({ products: mockProducts, loading: false, error: null });
    renderProductListing('smartphones', store);
    expect(screen.getByText('iPhone 11')).toBeInTheDocument();
    expect(screen.getByText('Samsung Galaxy')).toBeInTheDocument();
  });

  test('renders product prices', () => {
    const store = createTestStore({ products: mockProducts, loading: false, error: null });
    renderProductListing('smartphones', store);
    expect(screen.getByText('$599')).toBeInTheDocument();
    expect(screen.getByText('$499')).toBeInTheDocument();
  });

  test('renders product descriptions', () => {
    const store = createTestStore({ products: mockProducts, loading: false, error: null });
    renderProductListing('smartphones', store);
    expect(screen.getByText('A great phone')).toBeInTheDocument();
    expect(screen.getByText('Another great phone')).toBeInTheDocument();
  });

  test('renders Add to Cart button for each product', () => {
    const store = createTestStore({ products: mockProducts, loading: false, error: null });
    renderProductListing('smartphones', store);
    expect(screen.getAllByText('Add to Cart')).toHaveLength(2);
  });

  test('dispatches addToCart action when Add to Cart clicked', () => {
    const store = createTestStore({ products: mockProducts, loading: false, error: null });
    renderProductListing('smartphones', store);
    const addButtons = screen.getAllByText('Add to Cart');
    fireEvent.click(addButtons[0]);
    expect(store.getState().cart.totalCount).toBe(1);
    expect(store.getState().cart.items[0].id).toBe(1);
  });

  test('increments cart count on multiple add to cart clicks', () => {
    const store = createTestStore({ products: mockProducts, loading: false, error: null });
    renderProductListing('smartphones', store);
    const addButtons = screen.getAllByText('Add to Cart');
    fireEvent.click(addButtons[0]);
    fireEvent.click(addButtons[0]);
    expect(store.getState().cart.totalCount).toBe(2);
  });
});
