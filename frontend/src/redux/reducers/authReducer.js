import {authAPI} from "../../api/api";
import {messagingApi} from "../../api/messagingApi";

const SET_USER_DATA = "SET_USER_DATA";
const RESET_USER_DATA = "RESET_USER_DATA";

let initialState = {
    userId: null,
    email: null,
    isAuth: false,
    roles: null
};

const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_USER_DATA:
            return {
                ...state,
                userId: action.userData.id,
                email: action.userData.email,
                isAuth: true,
                roles: [...action.userData.roles]
            }
        case RESET_USER_DATA:
            return {
                ...state,
                userId: null,
                email: null,
                isAuth: false,
                roles: null
            }
        default:
            return state;
    }
}

export const setUserData = (userData) => ({type: SET_USER_DATA, userData})
export const resetUserData = () => ({type: RESET_USER_DATA});

export const getUserData = () => (dispatch) => {
    return authAPI.me().then(response => {
        dispatch(setUserData(response.data))
    }).catch(reason => {
        console.error("Error downloading user data")
        console.error(reason)
    })
}

export const login = (email, password) => (dispatch) => {
    return authAPI.login(email, password).then(response => {
        dispatch(setUserData(response.data))
    }).catch(error => {
            console.error("Error in authentication process");
            const {response, message} = error;
            if (response) {
                return response.status;
            } else {
                return message;
            }
        }
    );
}

export const logout = () => (dispatch) => {
    authAPI.logout().then(() => {
        dispatch(resetUserData())
    }).catch(reason => {
        console.error("Error in logout process");
        console.error(reason);
    })
    messagingApi.disconnect();
}

export default authReducer;