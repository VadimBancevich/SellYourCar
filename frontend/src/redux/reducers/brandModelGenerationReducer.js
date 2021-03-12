import {carApi} from "../../api/api";

const SET_BRANDS = "SET_BRANDS"
const SET_MODELS = "SET_MODELS"
const SET_GENERATIONS = "SET_GENERATIONS"
const RESET_BRANDS = "RESET_BRANDS"
const RESET_MODELS = "RESET_MODELS"
// const SET_IDS = "SET_IDS";
const RESET_GENERATIONS = "RESET_GENERATIONS";
const SET_GENERATION_ID = "SET_GENERATION_ID";
const SET_ALL_BMG = "SET_ALL_BMG";

const initialState = {
    brands: [/*{id: 0, brandName: "someBrand"}*/],
    models: [/*{id: 0, modelName: "someModel"}*/],
    generations: [/*{id: 0, generationName: "someGeneration"}*/],
    brandId: null,
    modelId: null,
    generationId: null
}

const brandModelGenerationReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_BRANDS:
            return {
                ...state,
                brands: action.brands
            }
        case SET_MODELS:
            return {
                ...state,
                brandId: action.brandId,
                models: action.models
            }
        case RESET_MODELS:
            return {
                ...state,
                modelId: null,
                generationId: null,
                generations: []
            }
        case SET_GENERATIONS:
            return {
                ...state,
                modelId: action.modelId,
                generations: action.generations
            }
        case SET_GENERATION_ID:
            return {
                ...state,
                generationId: action.generationId
            }
        case RESET_BRANDS:
            return {
                ...state,
                brandId: null,
                modelId: null,
                generationId: null,
                models: [],
                generations: [],
            }
        case RESET_GENERATIONS:
            return {
                ...state,
                generationId: null
            }
        case SET_ALL_BMG: {
            return {
                ...state,
                brandId: action.brandId,
                modelId: action.modelId,
                generationId: action.generationId,
                brands: action.brands,
                models: action.models,
                generations: action.generations
            }
        }
        default:
            return state

    }
}

const setBrands = (brands) => ({type: SET_BRANDS, brands})
export const resetBrands = () => ({type: RESET_BRANDS})
export const setModels = (models, brandId) => ({type: SET_MODELS, models, brandId})
export const resetModels = () => ({type: RESET_MODELS})
export const setGenerations = (generations, modelId) => ({type: SET_GENERATIONS, generations, modelId})
// const setIds = (car) => ({type: SET_IDS, car})
export const resetGenerations = () => ({type: RESET_GENERATIONS})
export const setGenerationId = (generationId) => ({type: SET_GENERATION_ID, generationId})
export const setAllBMG = (brandId, modelId, generationId, brands, models, generations) =>
    ({type: SET_ALL_BMG, brandId, modelId, generationId, brands, models, generations})

export const getAllBrands = () => dispatch => {
    carApi.getBrands().then(response => dispatch(setBrands(response.data)))
}

export const getModelsByBrandId = brandId => dispatch => {
    carApi.getModelsByBrandId(brandId).then(response => dispatch(setModels(response.data, brandId)))
}

export const getGenerationsByModelId = modelId => dispatch => {
    carApi.getGenerationsByModelId(modelId).then(response => dispatch(setGenerations(response.data, modelId)))
}

export default brandModelGenerationReducer;