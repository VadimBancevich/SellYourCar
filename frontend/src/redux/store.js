import {reducer as formReducer} from 'redux-form';
import {applyMiddleware, combineReducers, createStore} from 'redux';
import authReducer from "./reducers/authReducer";
import thunk from "redux-thunk";
import appReducer from "./reducers/appReducer";
import carsReducer from "./reducers/carsReducer";
import chatsReducer from "./reducers/chatsReducer";
import messagesReducer from "./reducers/messagesReducer";
import imagesReducer from "./reducers/imagesReducer";
import carReducer from "./reducers/carReducer";
import updateCarReducer from "./reducers/updateCarReducer";
import brandModelGenerationReducer from "./reducers/brandModelGenerationReducer";

let reducers = combineReducers({
    auth: authReducer,
    form: formReducer,
    app: appReducer,
    cars: carsReducer,
    car: carReducer,
    chats: chatsReducer,
    messages: messagesReducer,
    images: imagesReducer,
    updateCar: updateCarReducer,
    bmgen: brandModelGenerationReducer
});

let store = createStore(reducers, applyMiddleware(thunk));

export default store;