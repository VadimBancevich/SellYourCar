import {getUserData} from "./authReducer";

const INITIALIZE = 'INITIALIZE'

let initialState = {
    initialized: false
}

const appReducer = (state = initialState, action) => {
    switch (action.type) {
        case INITIALIZE:{
            return {
                ...state,
                initialized:true
            }
        }
        default:
            return state;
    }
}

export const initialize = () =>({type:INITIALIZE});

export const initializeApp = () => (dispatch) => {
    let promise = dispatch(getUserData());
    promise.then(() => dispatch(initialize()))
}

export default appReducer;