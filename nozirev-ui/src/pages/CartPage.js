import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { removeFromCart } from '../redux/actions/cartActions';
import '../styles/CartPage.css';

function CartPage() {
  const dispatch = useDispatch();
  const { items, totalCount } = useSelector((state) => state.cart);

  const totalPrice = items.reduce(
    (sum, item) => sum + item.amount * item.quantity,
    0
  );

  if (items.length === 0) {
    return (
      <div className="cart-page">
        <div className="cart-empty">
          <h2>Your cart is empty</h2>
          <p>Add some products to get started.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="cart-page">
      <div className="cart-header">
        <h1>Your Cart</h1>
        <span className="cart-item-count">{totalCount} item{totalCount !== 1 ? 's' : ''}</span>
      </div>

      <div className="cart-content">
        <div className="cart-items">
          {items.map((item) => (
            <div key={item.id} className="cart-item">
              <div className="cart-item-image">
                <img src={item.imageUrl} alt={item.productName} />
              </div>
              <div className="cart-item-details">
                <h3 className="cart-item-name">{item.productName}</h3>
                <p className="cart-item-description">{item.description}</p>
                <div className="cart-item-footer">
                  <span className="cart-item-price">${item.amount}</span>
                  <span className="cart-item-qty">x{item.quantity}</span>
                  <span className="cart-item-subtotal">= ${(item.amount * item.quantity).toFixed(2)}</span>
                </div>
              </div>
              <button
                className="remove-item-btn"
                onClick={() => dispatch(removeFromCart(item.id))}
                aria-label={`Remove ${item.productName} from cart`}
              >
                ✕
              </button>
            </div>
          ))}
        </div>

        <div className="cart-summary">
          <h2>Order Summary</h2>
          <div className="summary-row">
            <span>Items ({totalCount})</span>
            <span>${totalPrice.toFixed(2)}</span>
          </div>
          <div className="summary-divider" />
          <div className="summary-row summary-total">
            <span>Total</span>
            <span>${totalPrice.toFixed(2)}</span>
          </div>
          <button className="checkout-btn">Proceed to Checkout</button>
        </div>
      </div>
    </div>
  );
}

export default CartPage;
