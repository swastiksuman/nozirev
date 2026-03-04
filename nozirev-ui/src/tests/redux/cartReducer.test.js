import cartReducer, {
  ADD_TO_CART,
  REMOVE_FROM_CART,
} from '../../redux/reducers/cartReducer';

const mockProduct = { id: 1, productName: 'iPhone 11', amount: 599 };
const mockProduct2 = { id: 2, productName: 'Samsung Galaxy', amount: 499 };

describe('cartReducer', () => {
  const initialState = { items: [], totalCount: 0 };

  test('returns initial state for unknown action', () => {
    expect(cartReducer(undefined, {})).toEqual(initialState);
  });

  test('adds a new item to empty cart', () => {
    const state = cartReducer(initialState, { type: ADD_TO_CART, payload: mockProduct });
    expect(state.items).toHaveLength(1);
    expect(state.items[0].id).toBe(1);
    expect(state.items[0].quantity).toBe(1);
    expect(state.totalCount).toBe(1);
  });

  test('increments quantity for existing item', () => {
    const stateAfterFirst = cartReducer(initialState, { type: ADD_TO_CART, payload: mockProduct });
    const stateAfterSecond = cartReducer(stateAfterFirst, { type: ADD_TO_CART, payload: mockProduct });
    expect(stateAfterSecond.items).toHaveLength(1);
    expect(stateAfterSecond.items[0].quantity).toBe(2);
    expect(stateAfterSecond.totalCount).toBe(2);
  });

  test('adds multiple different products', () => {
    const stateAfterFirst = cartReducer(initialState, { type: ADD_TO_CART, payload: mockProduct });
    const stateAfterSecond = cartReducer(stateAfterFirst, { type: ADD_TO_CART, payload: mockProduct2 });
    expect(stateAfterSecond.items).toHaveLength(2);
    expect(stateAfterSecond.totalCount).toBe(2);
  });

  test('removes item from cart', () => {
    const stateWithItem = { items: [{ ...mockProduct, quantity: 1 }], totalCount: 1 };
    const state = cartReducer(stateWithItem, { type: REMOVE_FROM_CART, payload: 1 });
    expect(state.items).toHaveLength(0);
    expect(state.totalCount).toBe(0);
  });

  test('removes item and decrements totalCount by quantity', () => {
    const stateWithItem = { items: [{ ...mockProduct, quantity: 3 }], totalCount: 3 };
    const state = cartReducer(stateWithItem, { type: REMOVE_FROM_CART, payload: 1 });
    expect(state.totalCount).toBe(0);
  });

  test('removes correct item when multiple items exist', () => {
    const stateWithItems = {
      items: [{ ...mockProduct, quantity: 2 }, { ...mockProduct2, quantity: 1 }],
      totalCount: 3,
    };
    const state = cartReducer(stateWithItems, { type: REMOVE_FROM_CART, payload: 1 });
    expect(state.items).toHaveLength(1);
    expect(state.items[0].id).toBe(2);
    expect(state.totalCount).toBe(1);
  });

  test('does not change state when removing non-existent item', () => {
    const stateWithItem = { items: [{ ...mockProduct, quantity: 1 }], totalCount: 1 };
    const state = cartReducer(stateWithItem, { type: REMOVE_FROM_CART, payload: 999 });
    expect(state.items).toHaveLength(1);
    expect(state.totalCount).toBe(1);
  });

  test('preserves other item properties when adding to cart', () => {
    const state = cartReducer(initialState, { type: ADD_TO_CART, payload: mockProduct });
    expect(state.items[0].productName).toBe('iPhone 11');
    expect(state.items[0].amount).toBe(599);
  });
});
