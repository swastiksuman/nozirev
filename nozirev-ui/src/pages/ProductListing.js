import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Navigation from '../components/Navigation';
import Footer from '../components/Footer';
import { fetchProducts } from '../redux/actions/productActions';
import { addToCart } from '../redux/actions/cartActions';
import '../styles/ProductListing.css';

function ProductListing({ category = 'smartphones' }) {
  const dispatch = useDispatch();
  const { products, loading, error } = useSelector((state) => state.products);

  useEffect(() => {
    dispatch(fetchProducts(category));
  }, [dispatch, category]);

  return (
    <div className="product-listing-container">
      <Navigation />

      {/* Product Listing Section */}
      <section className="product-listing">
        <div className="listing-header">
          <h1>{{category}}</h1>
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
                    <button className="add-to-cart" onClick={() => dispatch(addToCart(product))}>Add to Cart</button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </section>

      <Footer />
    </div>
  );
}

export default ProductListing;
