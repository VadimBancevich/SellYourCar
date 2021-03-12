import {carApi} from "../../api/api";
import {setAllBMG, setGenerationId, setGenerations, setModels} from "./brandModelGenerationReducer";
import {initImages} from "./imagesReducer";

const SET_UPDATE_CAR = "SET_UPDATE_CAR"
const RESET_UPDATE_CAR = "RESET_UPDATE_CAR"
const SET_UPDATE_CAR_LOADING = "SET_UPDATE_CAR_LOADING";

const initialState = {
    isLoading: true,
    car: null
}

const updateCarReducer = (state = initialState, action) => {

    switch (action.type) {
        case SET_UPDATE_CAR:
            return {
                ...state,
                car: action.car,
                isLoading: false
            }
        case SET_UPDATE_CAR_LOADING:
            return {
                ...state,
                isLoading: action.isLoading
            }
        case RESET_UPDATE_CAR:
            return initialState;
        default:
            return state
    }
}

const setCar = (car) => ({type: SET_UPDATE_CAR, car})
export const resetUpdateCar = () => ({type: RESET_UPDATE_CAR})
export const setUpdateCarLoading = (isLoading) => ({type: SET_UPDATE_CAR_LOADING, isLoading})

export const getMyCarById = carId => dispatch => {
    carApi.getMyCar(carId)
        .then(response => {
            const car = response.data;
            const brandsPromise = carApi.getBrands();
            const modelsPromise = carApi.getModelsByBrandId(car.brandId);
            const generationsPromise = carApi.getGenerationsByModelId(car.modelId);
            Promise.all([brandsPromise, modelsPromise, generationsPromise]).then(responseArray => {
                const brands = responseArray[0].data
                const models = responseArray[1].data
                const generations = responseArray[2].data
                dispatch(setAllBMG(car.brandId, car.modelId, car.generationId, brands, models, generations));
                dispatch(setCar(car))
                dispatch(initImages(car.images))
            }).catch(reason => dispatch(setCar(null)))
        })
}

export default updateCarReducer;