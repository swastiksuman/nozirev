import { fetchProducts } from '../../redux/actions/productActions';
import {
  FETCH_PRODUCTS_REQUEST,
  FETCH_PRODUCTS_SUCCESS,
  FETCH_PRODUCTS_ERROR,
} from '../../redux/reducers/productReducer';
import * as productService from '../../services/productService';

describe('productActions - fetchProducts', () => {
  let dispatch;

  beforeEach(() => {
    dispatch = jest.fn();
    jest.spyOn(productService, 'fetchProductList');
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  test('dispatches FETCH_PRODUCTS_REQUEST first', async () => {
    productService.fetchProductList.mockResolvedValue([]);
    await fetchProducts('smartphones')(dispatch);
    expect(dispatch).toHaveBeenNthCalledWith(1, { type: FETCH_PRODUCTS_REQUEST });
  });

  test('dispatches FETCH_PRODUCTS_SUCCESS with data on success', async () => {
    const mockData = [{ id: 1, productName: 'iPhone 11' }];
    productService.fetchProductList.mockResolvedValue(mockData);
    await fetchProducts('smartphones')(dispatch);
    expect(dispatch).toHaveBeenNthCalledWith(2, { type: FETCH_PRODUCTS_SUCCESS, payload: mockData });
  });

  test('dispatches FETCH_PRODUCTS_ERROR with message on failure', async () => {
    productService.fetchProductList.mockRejectedValue(new Error('Network error'));
    await fetchProducts('smartphones')(dispatch);
    expect(dispatch).toHaveBeenNthCalledWith(2, { type: FETCH_PRODUCTS_ERROR, payload: 'Network error' });
  });

  test('calls fetchProductList with the provided category', async () => {
    productService.fetchProductList.mockResolvedValue([]);
    await fetchProducts('tablets')(dispatch);
    expect(productService.fetchProductList).toHaveBeenCalledWith('tablets');
  });

  test('defaults to smartphones category', async () => {
    productService.fetchProductList.mockResolvedValue([]);
    await fetchProducts()(dispatch);
    expect(productService.fetchProductList).toHaveBeenCalledWith('smartphones');
  });

  test('dispatches exactly two actions on success', async () => {
    productService.fetchProductList.mockResolvedValue([]);
    await fetchProducts('smartphones')(dispatch);
    expect(dispatch).toHaveBeenCalledTimes(2);
  });

  test('dispatches exactly two actions on failure', async () => {
    productService.fetchProductList.mockRejectedValue(new Error('Error'));
    await fetchProducts('smartphones')(dispatch);
    expect(dispatch).toHaveBeenCalledTimes(2);
  });
});
