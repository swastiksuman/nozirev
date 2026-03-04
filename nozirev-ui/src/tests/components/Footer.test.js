import React from 'react';
import { render, screen } from '@testing-library/react';
import Footer from '../../components/Footer';

describe('Footer Component', () => {
  test('renders footer element', () => {
    render(<Footer />);
    expect(screen.getByRole('contentinfo')).toBeInTheDocument();
  });

  test('renders copyright year', () => {
    render(<Footer />);
    expect(screen.getByText(/2025/)).toBeInTheDocument();
  });

  test('renders Nozirev brand name', () => {
    render(<Footer />);
    expect(screen.getByText(/Nozirev/)).toBeInTheDocument();
  });

  test('renders "All rights reserved" text', () => {
    render(<Footer />);
    expect(screen.getByText(/All rights reserved/i)).toBeInTheDocument();
  });
});
