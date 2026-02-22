const initialState = {
  items: [],
  totalCount: 0,
};

export const ADD_TO_CART = 'ADD_TO_CART';
export const REMOVE_FROM_CART = 'REMOVE_FROM_CART';

export default function cartReducer(state = initialState, action) {
  switch (action.type) {
    case ADD_TO_CART: {
      const existingItem = state.items.find((item) => item.id === action.payload.id);
      const updatedItems = existingItem
        ? state.items.map((item) =>
            item.id === action.payload.id
              ? { ...item, quantity: item.quantity + 1 }
              : item
          )
        : [...state.items, { ...action.payload, quantity: 1 }];
      return {
        ...state,
        items: updatedItems,
        totalCount: state.totalCount + 1,
      };
    }
    case REMOVE_FROM_CART: {
      const updatedItems = state.items.filter((item) => item.id !== action.payload);
      const removedItem = state.items.find((item) => item.id === action.payload);
      return {
        ...state,
        items: updatedItems,
        totalCount: state.totalCount - (removedItem ? removedItem.quantity : 0),
      };
    }
    default:
      return state;
  }
}
