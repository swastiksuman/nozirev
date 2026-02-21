import { createStore, combineReducers, applyMiddleware } from 'redux';
import { thunk } from 'redux-thunk';
import productReducer from './reducers/productReducer';

const rootReducer = combineReducers({
  products: productReducer,
});

const middleware = applyMiddleware(thunk);
const store = createStore(rootReducer, middleware);

export default store;
