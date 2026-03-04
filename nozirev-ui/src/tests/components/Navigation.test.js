import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { createStore, combineReducers } from 'redux';
import Navigation from '../../components/Navigation';
import cartReducer from '../../redux/reducers/cartReducer';
import productReducer from '../../redux/reducers/productReducer';

const createTestStore = (cartState = { items: [], totalCount: 0 }) =>
  createStore(
    combineReducers({ cart: cartReducer, products: productReducer }),
    { cart: cartState }
  );

const renderNav = (store = createTestStore()) =>
  render(
    <Provider store={store}>
      <MemoryRouter>
        <Navigation />
      </MemoryRouter>
    </Provider>
  );

describe('Navigation Component', () => {
  test('renders nav element', () => {
    renderNav();
    expect(screen.getByRole('navigation')).toBeInTheDocument();
  });

  test('renders Home nav link', () => {
    renderNav();
    expect(screen.getByText('Home')).toBeInTheDocument();
  });

  test('renders Shop nav link', () => {
    renderNav();
    expect(screen.getByText('Shop')).toBeInTheDocument();
  });

  test('renders About nav link', () => {
    renderNav();
    expect(screen.getByText('About')).toBeInTheDocument();
  });

  test('renders Contact nav link', () => {
    renderNav();
    expect(screen.getByText('Contact')).toBeInTheDocument();
  });

  test('renders cart button with emoji', () => {
    renderNav();
    expect(screen.getByText('🛒')).toBeInTheDocument();
  });

  test('does not show cart count badge when cart is empty', () => {
    renderNav(createTestStore({ items: [], totalCount: 0 }));
    expect(screen.queryByText('0')).not.toBeInTheDocument();
  });

  test('shows cart count badge when cart has items', () => {
    renderNav(createTestStore({ items: [], totalCount: 3 }));
    expect(screen.getByText('3')).toBeInTheDocument();
  });

  test('shows dropdown items on Shop mouse enter', () => {
    renderNav();
    const shopItem = screen.getByText('Shop').closest('li');
    fireEvent.mouseEnter(shopItem);
    expect(screen.getByText('Smartphone')).toBeInTheDocument();
    expect(screen.getByText('Tablets')).toBeInTheDocument();
    expect(screen.getByText('Watches')).toBeInTheDocument();
  });

  test('hides dropdown items on Shop mouse leave', () => {
    renderNav();
    const shopItem = screen.getByText('Shop').closest('li');
    fireEvent.mouseEnter(shopItem);
    fireEvent.mouseLeave(shopItem);
    expect(screen.queryByText('Smartphone')).not.toBeInTheDocument();
    expect(screen.queryByText('Tablets')).not.toBeInTheDocument();
    expect(screen.queryByText('Watches')).not.toBeInTheDocument();
  });

  test('dropdown is hidden by default', () => {
    renderNav();
    expect(screen.queryByText('Smartphone')).not.toBeInTheDocument();
  });
});
