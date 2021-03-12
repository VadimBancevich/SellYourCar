import {carApi} from "../../api/api";
import {initImages} from "./imagesReducer";

const SET_CAR = "SET_CAR";
const SET_IS_LOADING = "SET_IS_LOADING";
const RESET_CAR_STATE = "RESET_CAR_STATE";

let initialState = {
    isLoading: true,
    car: null

}

const carReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_CAR:
            return {
                isLoading: false,
                car: action.car
            }
        case SET_IS_LOADING:
            return {
                ...state,
                isLoading: action.isLoading
            }
        case RESET_CAR_STATE :
            return {
                isLoading: false,
                car: null
            }
        default:
            return state
    }
}

const setCar = (car) => ({type: SET_CAR, car})
const setIsLoading = (isLoading) => ({type: SET_IS_LOADING, isLoading})
export const resetCarState = () => ({type: RESET_CAR_STATE})

export const getCarById = carId => dispatch => {
    dispatch(setIsLoading(true))
    carApi.getCarById(carId).then(response => {
        dispatch(setCar(response.data))
        dispatch(initImages(response.data.images))
    })
}

export default carReducer;