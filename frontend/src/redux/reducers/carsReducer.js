import {carApi} from "../../api/api";

const SET_CARS = "SET_CARS";
const SET_CAR_PAGE = "SET_CAR_PAGE";

let initialState = {
    cars: [],
    page: 1,
    pageSize: 20,
    countPages: 1
}

const carsReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_CARS:
            if (action.page.pageNumber === state.page) {
                return {
                    cars: action.page.content,
                    page: action.page.pageNumber,
                    pageSize: action.page.pageSize,
                    countPages: action.page.countPages
                }
            }
        case SET_CAR_PAGE : {
            return {
                ...state,
                page: action.page
            }
        }
        default :
            return state
    }
}

const setCars = (page) => ({type: SET_CARS, page})
export const setCarPage = page => ({type: SET_CAR_PAGE, page})

export const initCars = () => (dispatch) => {
    carApi.findCars().then(response => {
        dispatch(setCars(response.data));
    })
}
export const findCars = (query) => (dispatch) => {
    carApi.findCars(query).then(response => {
        dispatch(setCars(response.data));
    })
}

export default carsReducer;