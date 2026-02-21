# Project Folder Structure & Standards

This document outlines the folder structure and coding standards for the Nozirev React application.

## Directory Structure

```
nozirev-ui/
├── src/
│   ├── components/          # Reusable UI components
│   │   ├── Navigation.js
│   │   └── Footer.js
│   ├── pages/              # Full page components/routes
│   │   ├── HomePage.js
│   │   └── ProductListing.js
│   ├── services/           # API calls and external services
│   │   └── productService.js
│   ├── redux/              # Redux state management
│   │   ├── store.js        # Redux store configuration
│   │   ├── actions/        # Redux action creators
│   │   │   └── productActions.js
│   │   └── reducers/       # Redux reducers
│   │       └── productReducer.js
│   ├── styles/             # CSS files (organized by component)
│   │   ├── Navigation.css
│   │   ├── Footer.css
│   │   ├── HomePage.css
│   │   └── ProductListing.css
│   ├── utils/              # Helper functions and utilities
│   ├── App.js              # Main app component with routing
│   ├── App.css             # Global styles
│   └── index.js            # Entry point
├── public/                 # Static assets
├── package.json
└── README.md
```

## Folder Purposes

### `/components`
Contains reusable UI components that are used across multiple pages. Each component should be self-contained and focused on a single responsibility.

**Guidelines:**
- One component per file
- Use meaningful component names (PascalCase)
- Keep components focused and reusable
- Import associated CSS file in each component

**Example:**
```javascript
// Navigation.js
import '../styles/Navigation.css';

function Navigation() {
  // Component logic
}
export default Navigation;
```

### `/pages`
Contains full page components that represent routes. These are typically composed of multiple smaller components.

**Guidelines:**
- One page per route
- Use meaningful page names (PascalCase)
- Pages should compose components together
- Import Navigation and Footer for consistent layout

### `/services`
Contains API calls and external service integrations. This layer abstracts backend communication.

**Guidelines:**
- One service file per major feature
- Use meaningful function names (camelCase)
- Return promises or handle errors gracefully
- Support environment-based configuration

**Example:**
```javascript
// productService.js
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export const fetchProductList = async () => {
  const response = await fetch(`${API_BASE_URL}/getProductList`);
  if (!response.ok) throw new Error('Failed to fetch');
  return await response.json();
};
```

### `/redux`

#### `/redux/store.js`
Configures the Redux store with all reducers and middleware (thunk).

#### `/redux/reducers`
Contains Redux reducers that manage state. Each reducer file should:
- Define initial state
- Export action type constants
- Export default reducer function
- Handle all related actions

**Example:**
```javascript
// productReducer.js
const initialState = {
  products: [],
  loading: false,
  error: null,
};

export const FETCH_PRODUCTS_REQUEST = 'FETCH_PRODUCTS_REQUEST';
export const FETCH_PRODUCTS_SUCCESS = 'FETCH_PRODUCTS_SUCCESS';
export const FETCH_PRODUCTS_ERROR = 'FETCH_PRODUCTS_ERROR';

export default function productReducer(state = initialState, action) {
  switch (action.type) {
    case FETCH_PRODUCTS_REQUEST:
      return { ...state, loading: true, error: null };
    case FETCH_PRODUCTS_SUCCESS:
      return { ...state, loading: false, products: action.payload };
    case FETCH_PRODUCTS_ERROR:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
}
```

#### `/redux/actions`
Contains Redux action creators. Async actions use Redux Thunk.

**Guidelines:**
- Export action functions (camelCase)
- Use dispatch to send actions to reducers
- Handle async operations with async/await
- Always handle errors

**Example:**
```javascript
// productActions.js
import { fetchProductList } from '../../services/productService';
import {
  FETCH_PRODUCTS_REQUEST,
  FETCH_PRODUCTS_SUCCESS,
  FETCH_PRODUCTS_ERROR,
} from '../reducers/productReducer';

export const fetchProducts = () => async (dispatch) => {
  dispatch({ type: FETCH_PRODUCTS_REQUEST });
  try {
    const data = await fetchProductList();
    dispatch({ type: FETCH_PRODUCTS_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: FETCH_PRODUCTS_ERROR, payload: error.message });
  }
};
```

### `/styles`
Contains CSS files organized by component. Each component's styles should be in a separate file with the same name.

**Guidelines:**
- One CSS file per component
- Match CSS filename to component name (e.g., `Navigation.js` → `Navigation.css`)
- Use meaningful class names (kebab-case)
- Keep styles scoped to specific components
- Use media queries for responsive design

### `/utils`
Contains helper functions, constants, and utilities used across the application.

**Guidelines:**
- Group related utilities together
- Use descriptive function names
- Add JSDoc comments for complex functions
- Keep functions pure and testable

## Naming Conventions

### Components & Pages
- **Format:** PascalCase
- **Example:** `ProductListing.js`, `Navigation.js`

### Functions & Variables
- **Format:** camelCase
- **Example:** `fetchProducts()`, `productList`

### CSS Classes
- **Format:** kebab-case
- **Example:** `.product-card`, `.nav-menu`

### Redux Action Types
- **Format:** UPPER_SNAKE_CASE
- **Example:** `FETCH_PRODUCTS_REQUEST`

### CSS Files
- **Format:** Match component name in PascalCase
- **Example:** `Navigation.css` for `Navigation.js`

## How to Add a New Feature

### 1. Create Redux Logic (if data is involved)
```bash
# Add reducer
src/redux/reducers/featureReducer.js

# Add action
src/redux/actions/featureActions.js
```

### 2. Create Service/API Layer
```bash
# Add service
src/services/featureService.js
```

### 3. Create Components
```bash
# Add reusable components
src/components/FeatureComponent.js

# Add styles
src/styles/FeatureComponent.css
```

### 4. Create Page (if it's a route)
```bash
# Add page
src/pages/FeaturePage.js

# Update App.js routing
```

### 5. Update Redux Store (if new reducer)
In `src/redux/store.js`, add the new reducer to `combineReducers()`

## Component Usage Example

### Using Redux in a Component
```javascript
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProducts } from '../redux/actions/productActions';

function ProductListing() {
  const dispatch = useDispatch();
  const { products, loading, error } = useSelector((state) => state.products);

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div>
      {loading && <p>Loading...</p>}
      {error && <p>Error: {error}</p>}
      {products.map((product) => (
        <div key={product.id}>{product.productName}</div>
      ))}
    </div>
  );
}
export default ProductListing;
```

## Environment Variables

Create a `.env` file in the root directory:

```
REACT_APP_API_URL=http://localhost:8080
```

Access in code:
```javascript
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
```

## Best Practices

1. **Keep components small and focused**
   - One responsibility per component
   - Reuse components across pages

2. **Centralize state in Redux**
   - Use Redux for global/shared state
   - Use local state only for UI-specific data

3. **Separate concerns**
   - Business logic in Redux actions
   - API calls in services
   - UI logic in components

4. **Import order**
   - External imports first (React, Redux, etc.)
   - Internal imports from services
   - Relative imports (components, styles)

5. **CSS Organization**
   - Keep styles close to components
   - Use responsive design patterns
   - Avoid inline styles

6. **Error Handling**
   - Always handle API errors
   - Provide user feedback
   - Log errors to console for debugging

## Running the Application

```bash
# Install dependencies
npm install

# Start development server
npm start

# Build for production
npm build

# Run tests
npm test
```

## Additional Resources

- [React Documentation](https://react.dev)
- [Redux Documentation](https://redux.js.org)
- [React Router Documentation](https://reactrouter.com)
