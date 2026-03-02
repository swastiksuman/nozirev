import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import NozirevLogo from '../assets/NozirevLogo';
import '../styles/Navigation.css';

function Navigation() {
  const navigate = useNavigate();
  const [showDropdown, setShowDropdown] = useState(false);
  const totalCount = useSelector((state) => state.cart.totalCount);

  const handleNavClick = (path) => {
    navigate(path);
  };

  return (
    <nav className="navbar">
      <div className="nav-container">
        <div className="logo" onClick={() => handleNavClick('/')} role="button" tabIndex={0}>
          <NozirevLogo height={36} />
        </div>
        <ul className="nav-menu">
          <li className="nav-item">
            <button className="nav-link" onClick={() => handleNavClick('/')}>Home</button>
          </li>
          <li 
            className="nav-item dropdown"
            onMouseEnter={() => setShowDropdown(true)}
            onMouseLeave={() => setShowDropdown(false)}
          >
            <button className="nav-link">Shop</button>
            {showDropdown && (
              <ul className="dropdown-menu">
                <li><button onClick={() => handleNavClick('/smartphones')} className="dropdown-link">Smartphone</button></li>
                <li><button onClick={() => handleNavClick('/tablets')} className="dropdown-link">Tablets</button></li>
                <li><button onClick={() => handleNavClick('/watches')} className="dropdown-link">Watches</button></li>
              </ul>
            )}
          </li>
          <li className="nav-item">
            <button className="nav-link">About</button>
          </li>
          <li className="nav-item">
            <button className="nav-link">Contact</button>
          </li>
        </ul>
        <button className="cart-btn" onClick={() => handleNavClick('/cart')}>
          🛒
          {totalCount > 0 && <span className="cart-count">{totalCount}</span>}
        </button>
      </div>
    </nav>
  );
}

export default Navigation;
