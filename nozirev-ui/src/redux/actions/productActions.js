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
    dispatch({
      type: FETCH_PRODUCTS_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: FETCH_PRODUCTS_ERROR,
      payload: error.message,
    });
  }
};
