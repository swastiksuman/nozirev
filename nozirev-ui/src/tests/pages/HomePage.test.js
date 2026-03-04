import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import HomePage from '../../pages/HomePage';

const renderHomePage = () =>
  render(
    <MemoryRouter>
      <HomePage />
    </MemoryRouter>
  );

describe('HomePage Component', () => {
  test('renders hero heading', () => {
    renderHomePage();
    expect(screen.getByText('Welcome to Nozirev')).toBeInTheDocument();
  });

  test('renders hero subtitle', () => {
    renderHomePage();
    expect(screen.getByText(/Discover the latest gadgets and accessories/i)).toBeInTheDocument();
  });

  test('renders Shop Now CTA button', () => {
    renderHomePage();
    expect(screen.getByText('Shop Now')).toBeInTheDocument();
  });

  test('renders Featured Products heading', () => {
    renderHomePage();
    expect(screen.getByText('Featured Products')).toBeInTheDocument();
  });

  test('renders Latest Smartphones card', () => {
    renderHomePage();
    expect(screen.getByText('Latest Smartphones')).toBeInTheDocument();
  });

  test('renders Tablets card', () => {
    renderHomePage();
    expect(screen.getByText('Tablets')).toBeInTheDocument();
  });

  test('renders Smart Watches card', () => {
    renderHomePage();
    expect(screen.getByText('Smart Watches')).toBeInTheDocument();
  });

  test('renders three View More links', () => {
    renderHomePage();
    expect(screen.getAllByText('View More')).toHaveLength(3);
  });

  test('renders smartphones card description', () => {
    renderHomePage();
    expect(screen.getByText(/cutting-edge technology/i)).toBeInTheDocument();
  });

  test('renders tablets card description', () => {
    renderHomePage();
    expect(screen.getByText(/work and entertainment/i)).toBeInTheDocument();
  });

  test('renders watches card description', () => {
    renderHomePage();
    expect(screen.getByText(/smart watch collection/i)).toBeInTheDocument();
  });
});
