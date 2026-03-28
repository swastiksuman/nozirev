import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './redux/store';
import HomePage from './pages/HomePage';
import ProductListing from './pages/ProductListing';
import CartPage from './pages/CartPage';
import Navigation from './components/Navigation';
import Footer from './components/Footer';
import './App.css';

function App() {
  return (
    <Provider store={store}>
      <Router>
        <Navigation />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/smartphones" element={<ProductListing category="smartphones" />} />
          <Route path="/tablets" element={<ProductListing category="tablets" />} />
          <Route path="/watches" element={<ProductListing category="watches" />} />
          <Route path="/cart" element={<CartPage />} />
        </Routes>
        <Footer />
      </Router>
    </Provider>
  );
}

export default App;
