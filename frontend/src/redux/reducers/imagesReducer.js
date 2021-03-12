import {carApi, imagesApi} from "../../api/api";

const INIT_IMAGES = "INIT_IMAGES";
const NEXT_IMAGE = "NEXT_IMAGE";
const PREV_IMAGE = "PREV_IMAGE";
const ADD_IMAGE = "ADD_IMAGE";
const DELETE_SELECTED_IMAGE = "DELETE_SELECTED_IMAGE";
const SELECT_IMAGE_BY_INDEX = "SELECT_IMAGE_BY_INDEX";
const RESET_IMAGES = "RESET_IMAGES"

export const defImg = "https://stmartinblue.com/images/cars/default_car.jpg";

let initialState = {
    isLoading: true,
    defaultImage: defImg,
    images: [defImg],
    selectedImageIndex: 0
}

const imagesReducer = (state = initialState, action) => {
    let imgIndex = state.selectedImageIndex;
    let imgCount = state.images.length;
    switch (action.type) {
        case INIT_IMAGES:
            return {
                ...state,
                images: action.images.length === 0 ? [state.defaultImage] : action.images,
                selectedImageIndex: 0
            }
        case NEXT_IMAGE :
            return {
                ...state,
                selectedImageIndex: imgIndex < imgCount - 1 ? imgIndex + 1 : 0
            }
        case PREV_IMAGE :
            return {
                ...state,
                selectedImageIndex: imgIndex > 0 && imgCount - 1 > 0 ? imgIndex - 1 : imgCount - 1
            }
        case ADD_IMAGE :
            return {
                ...state,
                images: state.images.includes(defImg) ? [action.image] : state.images.concat(action.image),
                selectedImageIndex: state.images.includes(defImg) ? 0 : imgIndex + 1
            }
        case DELETE_SELECTED_IMAGE :
            URL.revokeObjectURL(state.images[imgIndex])
            state.images.splice(imgIndex, 1)
            return {
                ...state,
                images: state.images.length === 0 ? [defImg] : [...state.images],
                selectedImageIndex: imgIndex > 0 ? imgIndex - 1 : 0
            }
        case SELECT_IMAGE_BY_INDEX:
            return {
                ...state,
                selectedImageIndex: action.index
            }
        case RESET_IMAGES:
            return initialState
            // return {
            //     ...state,
            //     images: [],
            //     selectedImageIndex: 0
            // }
        default:
            return state;
    }
}

export const initImages = (images) => ({type: INIT_IMAGES, images})
export const nextImage = () => ({type: NEXT_IMAGE})
export const prevImage = () => ({type: PREV_IMAGE})
export const addImage = (image) => ({type: ADD_IMAGE, image})
export const selectImageByIndex = (index) => ({type: SELECT_IMAGE_BY_INDEX, index})
export const resetImages = () => ({type: RESET_IMAGES})

export const deleteSelectedImage = (image, carId) => dispatch => {
    if (image.startsWith("blob")) {
        dispatch({type: DELETE_SELECTED_IMAGE})
    } else if (image && carId && image !== defImg) {
        imagesApi.deleteImage(image, carId).then(response => {
            dispatch({type: DELETE_SELECTED_IMAGE})
        })
    } else {
        console.error("Error during delete image")
    }
}


export default imagesReducer;