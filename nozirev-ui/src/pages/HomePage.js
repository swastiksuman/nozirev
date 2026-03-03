import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/HomePage.css';

function HomePage() {
  const navigate = useNavigate();

  const handleSmartphoneClick = (e) => {
    e.preventDefault();
    navigate('/smartphones');
  };

  const handleTabletClick = (e) => {
    console.log('Tablet link clicked');
    e.preventDefault();
    navigate('/tablets');
  };

  const handleWatchClick = (e) => {
    e.preventDefault();
    navigate('/watches');
  };

  return (
    <div className="home-page">

      {/* Hero Section */}
      <section className="hero">
        <div className="hero-content">
          <h1>Welcome to Nozirev</h1>
          <p>Discover the latest gadgets and accessories</p>
          <button className="cta-button">Shop Now</button>
        </div>
      </section>

      {/* Featured Products Section */}
      <section className="featured-products">
        <h2>Featured Products</h2>
        <div className="products-grid">
          <div className="product-card">
            <div className="product-image smartphone"></div>
            <h3>Latest Smartphones</h3>
            <p>Premium quality smartphones with cutting-edge technology</p>
            <a href="#" onClick={handleSmartphoneClick} className="product-link">View More</a>
          </div>
          <div className="product-card">
            <div className="product-image tablet"></div>
            <h3>Tablets</h3>
            <p>High-performance tablets for work and entertainment</p>
            <a href="#tablets" onClick={handleTabletClick} className="product-link">View More</a>
          </div>
          <div className="product-card">
            <div className="product-image watch"></div>
            <h3>Smart Watches</h3>
            <p>Stay connected with our smart watch collection</p>
            <a href="#watches" onClick={handleWatchClick} className="product-link">View More</a>
          </div>
        </div>
      </section>

    </div>
  );
}

export default HomePage;
