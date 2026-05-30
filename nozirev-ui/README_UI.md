# Nozirev UI — Overview

## Quick start

- Install: `npm install`
- Start dev server: `npm start`
- Run tests: `npm test`
- Build production: `npm run build`

Commands:

```bash
cd nozirev-ui
npm install
npm start
```

## Project layout

- `public/` — static HTML and assets.
- `src/`
  - `index.js` — app bootstrap and render.
  - `App.js` — routes and layout.
  - `pages/` — route-level pages (HomePage, ProductListing, CartPage).
  - `components/` — reusable components (Navigation, Footer).
  - `redux/`
    - `store.js` — configure Redux store
    - `actions/` — action creators and thunks
    - `reducers/` — state reducers
  - `services/` — API wrappers (productService.js)
  - `styles/` — CSS files
  - `tests/` — unit and integration tests

## Key files
- `src/index.js`
- `src/App.js`
- `src/pages/ProductListing.js`
- `src/redux/store.js`
- `src/redux/actions/productActions.js`
- `src/redux/reducers/productReducer.js`
- `src/services/productService.js`

## Data flow
1. UI dispatches an action (e.g., `fetchProducts()`).
2. Action uses `productService` to fetch data.
3. Reducer stores `products`, `loading`, and `error`.
4. Components read state via `useSelector`.

## Styling

- Uses plain CSS files in `src/styles` and component-level `.css`.

## Tests

- Tests use Jest + React Testing Library.
- Run: `npm test`

## Adding a new page (example)
1. Add `src/pages/NewPage.js`.
2. Create `src/styles/NewPage.css`.
3. Add route in `src/App.js`.
4. If needed, add actions/reducer and wire them into `src/redux/store.js`.

## Troubleshooting
- Dev server won't start: check `node` version and `npm install` output.
- API failures: check `productService.js` base URL and CORS config.

## Contact
If you need a deeper code walkthrough, specify files to inspect and I'll expand this README with code snippets.
