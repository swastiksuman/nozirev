import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './redux/store';
import HomePage from './pages/HomePage';
import ProductListing from './pages/ProductListing';
import './App.css';

function App() {
  return (
    <Provider store={store}>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/smartphones" element={<ProductListing />} />
        </Routes>
      </Router>
    </Provider>
  );
}

export default App;
