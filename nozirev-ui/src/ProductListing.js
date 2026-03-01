import React, { useState, useEffect } from 'react';
import './ProductListing.css';

function ProductListing() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const response = await fetch('http://localhost:8080/api/getProductList', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          type: 'smartphones',
        }),
      });
      
      if (!response.ok) {
        throw new Error('Failed to fetch products');
      }
      
      const data = await response.json();
      setProducts(data);
      setError(null);
    } catch (err) {
      setError(err.message);
      console.error('Error fetching products:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="product-listing-container">
      {/* Navigation Header */}
      <nav className="navbar">
        <div className="nav-container">
          <div className="logo">Nozirev</div>
          <ul className="nav-menu">
            <li className="nav-item">
              <a href="/" className="nav-link">Home</a>
            </li>
            <li className="nav-item">
              <a href="/smartphones" className="nav-link">Shop</a>
            </li>
            <li className="nav-item">
              <a href="#about" className="nav-link">About</a>
            </li>
            <li className="nav-item">
              <a href="#contact" className="nav-link">Contact</a>
            </li>
          </ul>
        </div>
      </nav>

      {/* Product Listing Section */}
      <section className="product-listing">
        <div className="listing-header">
          <h1>Smartphones</h1>
          <p>Browse our collection of latest smartphones</p>
        </div>

        {loading && <div className="loading">Loading products...</div>}
        
        {error && <div className="error">Error: {error}</div>}
        
        {!loading && !error && products.length === 0 && (
          <div className="no-products">No products available</div>
        )}

        {!loading && !error && products.length > 0 && (
          <div className="products-grid">
            {products.map((product) => (
              <div key={product.id} className="product-card">
                <div className="product-image">
                  <img src={product.imageUrl} alt={product.productName} />
                </div>
                <div className="product-details">
                  <h3 className="product-name">{product.productName}</h3>
                  <p className="product-description">{product.description}</p>
                  <div className="product-footer">
                    <span className="product-price">${product.amount}</span>
                    <button className="add-to-cart">Add to Cart</button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </section>

      {/* Footer */}
      <footer className="footer">
        <p>&copy; 2025 Nozirev. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default ProductListing;
