import productReducer, {
  FETCH_PRODUCTS_REQUEST,
  FETCH_PRODUCTS_SUCCESS,
  FETCH_PRODUCTS_ERROR,
} from '../../redux/reducers/productReducer';

const mockProducts = [
  { id: 1, productName: 'iPhone 11', imageUrl: 'https://example.com', amount: 599, description: 'Test' },
  { id: 2, productName: 'Samsung Galaxy', imageUrl: 'https://example.com', amount: 499, description: 'Test 2' },
];

describe('productReducer', () => {
  const initialState = { products: [], loading: false, error: null };

  test('returns initial state for unknown action', () => {
    expect(productReducer(undefined, {})).toEqual(initialState);
  });

  test('sets loading to true on FETCH_PRODUCTS_REQUEST', () => {
    const state = productReducer(initialState, { type: FETCH_PRODUCTS_REQUEST });
    expect(state.loading).toBe(true);
  });

  test('clears error on FETCH_PRODUCTS_REQUEST', () => {
    const stateWithError = { ...initialState, error: 'Old error' };
    const state = productReducer(stateWithError, { type: FETCH_PRODUCTS_REQUEST });
    expect(state.error).toBeNull();
  });

  test('keeps products during loading', () => {
    const stateWithProducts = { products: mockProducts, loading: false, error: null };
    const state = productReducer(stateWithProducts, { type: FETCH_PRODUCTS_REQUEST });
    expect(state.products).toEqual(mockProducts);
  });

  test('sets products on FETCH_PRODUCTS_SUCCESS', () => {
    const loadingState = { products: [], loading: true, error: null };
    const state = productReducer(loadingState, { type: FETCH_PRODUCTS_SUCCESS, payload: mockProducts });
    expect(state.products).toEqual(mockProducts);
  });

  test('sets loading to false on FETCH_PRODUCTS_SUCCESS', () => {
    const loadingState = { products: [], loading: true, error: null };
    const state = productReducer(loadingState, { type: FETCH_PRODUCTS_SUCCESS, payload: mockProducts });
    expect(state.loading).toBe(false);
  });

  test('clears error on FETCH_PRODUCTS_SUCCESS', () => {
    const errorState = { products: [], loading: false, error: 'Some error' };
    const state = productReducer(errorState, { type: FETCH_PRODUCTS_SUCCESS, payload: mockProducts });
    expect(state.error).toBeNull();
  });

  test('sets error on FETCH_PRODUCTS_ERROR', () => {
    const loadingState = { products: [], loading: true, error: null };
    const state = productReducer(loadingState, { type: FETCH_PRODUCTS_ERROR, payload: 'Network error' });
    expect(state.error).toBe('Network error');
  });

  test('sets loading to false on FETCH_PRODUCTS_ERROR', () => {
    const loadingState = { products: [], loading: true, error: null };
    const state = productReducer(loadingState, { type: FETCH_PRODUCTS_ERROR, payload: 'Network error' });
    expect(state.loading).toBe(false);
  });

  test('preserves existing products on FETCH_PRODUCTS_ERROR', () => {
    const stateWithProducts = { products: mockProducts, loading: true, error: null };
    const state = productReducer(stateWithProducts, { type: FETCH_PRODUCTS_ERROR, payload: 'Error' });
    expect(state.products).toEqual(mockProducts);
  });
});
