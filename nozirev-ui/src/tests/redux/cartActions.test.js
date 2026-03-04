import { addToCart, removeFromCart } from '../../redux/actions/cartActions';
import { ADD_TO_CART, REMOVE_FROM_CART } from '../../redux/reducers/cartReducer';

describe('cartActions', () => {
  const mockProduct = { id: 1, productName: 'iPhone 11', amount: 599 };

  describe('addToCart', () => {
    test('creates an ADD_TO_CART action', () => {
      const action = addToCart(mockProduct);
      expect(action.type).toBe(ADD_TO_CART);
    });

    test('includes product as payload', () => {
      const action = addToCart(mockProduct);
      expect(action.payload).toEqual(mockProduct);
    });

    test('returns a plain action object', () => {
      const action = addToCart(mockProduct);
      expect(action).toEqual({ type: ADD_TO_CART, payload: mockProduct });
    });
  });

  describe('removeFromCart', () => {
    test('creates a REMOVE_FROM_CART action', () => {
      const action = removeFromCart(1);
      expect(action.type).toBe(REMOVE_FROM_CART);
    });

    test('includes productId as payload', () => {
      const action = removeFromCart(1);
      expect(action.payload).toBe(1);
    });

    test('returns a plain action object', () => {
      const action = removeFromCart(1);
      expect(action).toEqual({ type: REMOVE_FROM_CART, payload: 1 });
    });
  });
});
